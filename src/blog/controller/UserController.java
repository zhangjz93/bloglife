package blog.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.enumeration.ErrorEnum;
import blog.model.User;
import blog.service.AttentionService;
import blog.service.CollectService;
import blog.service.DynamicService;
import blog.service.NoticeService;
import blog.service.UserService;
import blog.util.CookieUtil;
import blog.util.HttpUtil;
import blog.util.PropertiesLoader;

/** 
* 用户相关的Controller
* @author zjz 
*/ 
@Controller
public class UserController extends BaseController{
	private UserService userService;
	private CollectService collectService;
	private AttentionService attentionService;
	private NoticeService noticeService;
	private DynamicService dynamicService;
	private PropertiesLoader config;
	
	/**
	 * 后台验证是否处于登录状态，返回0为已登录
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="/isLogin", method=RequestMethod.GET)
	public void isLogin(HttpServletRequest req, HttpServletResponse res){
		User user = (User)req.getSession().getAttribute("user");
		if(user == null){
			writeMessage(false, res);
		}else{
			writeMessage(true, res);
		}
	}
	
	/**
	 * 登录
	 * @param req
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		if(user != null){
			return jumpToErrorPage(req, ErrorEnum.SESSION_EXIST);  //防止重复登陆
		}
		return "jsp/login.jsp";
	}
	
	/**
	 * 第三方登录
	 * @param req
	 * @return
	 */
	@RequestMapping("/githubLogin")
	public String goToGithubLogin(HttpServletRequest req, HttpServletResponse res){
		String code = req.getParameter("code");
		StringBuilder uri_1 = new StringBuilder("https://github.com/login/oauth/access_token?client_id=");
		//拼接请求字符串
		uri_1.append(config.getString("github.client_id"));
		uri_1.append("&client_secret=");
		uri_1.append(config.getString("github.client_secret"));
		uri_1.append("&code=");
		uri_1.append(code);
		String res_1 = HttpUtil.doGet(uri_1.toString());
		String token = HttpUtil.getParamMap(res_1).get("access_token");
		String uri_2 = "https://api.github.com/user?access_token=" + token;
		String res_2 = HttpUtil.doGet(uri_2);
		//解析返回的JSON信息并取得用户名
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject)parser.parse(res_2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String userName = (String)obj.get("login");
		String userPhoto = (String)obj.get("avatar_url");
		User user = new User();
		user.setName(userName);
		user.setPhoto(userPhoto);
		User loginUser = userService.login(user);
		req.getSession().setAttribute("user", loginUser);
		CookieUtil.addCookie("user", loginUser.getName(), "/", 259200, res);  //产生Cookie，默认保存3天
		return "redirect: index";
	}
	
	
	/**
	 * 退出登录并跳转至主页
	 * @param req
	 * @return
	 */
	@RequestMapping("/exit")
	public String exit(HttpServletRequest req, HttpServletResponse res){
		if(req.getSession().getAttribute("user") != null){
			req.getSession().removeAttribute("user");
			CookieUtil.addCookie("user", null, "/", 0, res);  //删除Cookie
		}
		return "redirect: index";
	}
	
	/**
	 * 转到个人中心首页
	 * @param uid
	 * @param req
	 * @return
	 */
	@RequestMapping("/person/info/{uid}")
	public String goToPersonInfoPage(@PathVariable("uid")int uid, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		Map<String,Object> map = null;
		if(user == null)
			map = userService.getUserInfo(uid, 0);
		else
			map = userService.getUserInfo(uid, user.getId());
		if(map == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		req.setAttribute("u", map.get("u"));
		req.setAttribute("focusNum", map.get("focusNum"));
		req.setAttribute("fansNum", map.get("fansNum"));
		req.setAttribute("articleNum", map.get("articleNum"));
		req.setAttribute("isFocused", map.get("isFocused"));
		return "jsp/person.jsp";
	}
	
	/**
	 * 更新个人信息，返回JSON
	 * @param u
	 * @param res
	 */
	@RequestMapping(value="/updateUser", method=RequestMethod.POST)
	public void updateUser(User u, HttpServletRequest req, HttpServletResponse res){
		User user = (User)req.getSession().getAttribute("user");
		u.setId(user.getId());
		res.setCharacterEncoding("utf-8");
		String json = userService.updateUser(u);
		writeMessage(json, res);
	}
	
	/**
	 * 转到个人收藏页面
	 * @param uid
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/person/collect")
	public String goToPersonCollectPage(int page, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		Map<String,Object> map = collectService.getCollectOfUser(user.getId(), 0, page);
		if(page < 1)
			page = 1;
		if(map == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> userMap = (Map<String,Object>)map.get("userMap");
		req.setAttribute("u", userMap.get("u"));
		req.setAttribute("focusNum", userMap.get("focusNum"));
		req.setAttribute("fansNum", userMap.get("fansNum"));
		req.setAttribute("articleNum", userMap.get("articleNum"));
		req.setAttribute("isFocused", userMap.get("isFocused"));
		req.setAttribute("articles", map.get("articles"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("collectNum", map.get("collectNum"));
		req.setAttribute("currentPage", page);
		return "jsp/person_collect.jsp";
	}
	
	/**
	 * 转到关注列表页
	 * @param uid
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/person/focus/{uid}")
	public String goToFocusPage(@PathVariable("uid")int uid, int page, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		Map<String,Object> map = null;
		if(page < 1)
			page = 1;
		if(user != null)
			map = attentionService.getFocusList(uid, user.getId(), page, true);
		else
			map = attentionService.getFocusList(uid, 0, page, true);
		if(map == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> userMap = (Map<String,Object>)map.get("userMap");
		req.setAttribute("u", userMap.get("u"));
		req.setAttribute("focusNum", userMap.get("focusNum"));
		req.setAttribute("fansNum", userMap.get("fansNum"));
		req.setAttribute("articleNum", userMap.get("articleNum"));
		req.setAttribute("isFocused", userMap.get("isFocused"));
		req.setAttribute("users", map.get("users"));
		req.setAttribute("attentionNum", map.get("attentionNum"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("currentPage", page);
		req.setAttribute("mode", "focus");
		return "jsp/person_attention.jsp";
	}
	
	/**
	 * 转到粉丝列表页
	 * @param uid
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/person/fans/{uid}")
	public String goToFansPage(@PathVariable("uid")int uid, int page, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		Map<String,Object> map = null;
		if(page < 1)
			page = 1;
		if(user != null)
			map = attentionService.getFocusList(uid, user.getId(), page, false);
		else
			map = attentionService.getFocusList(uid, 0, page, false);
		if(map == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> userMap = (Map<String,Object>)map.get("userMap");
		req.setAttribute("u", userMap.get("u"));
		req.setAttribute("focusNum", userMap.get("focusNum"));
		req.setAttribute("fansNum", userMap.get("fansNum"));
		req.setAttribute("articleNum", userMap.get("articleNum"));
		req.setAttribute("isFocused", userMap.get("isFocused"));
		req.setAttribute("users", map.get("users"));
		req.setAttribute("attentionNum", map.get("attentionNum"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("currentPage", page);
		req.setAttribute("mode", "fans");
		return "jsp/person_fans.jsp";
	}
	
	/**
	 * 添加关注
	 * @param focusedId
	 * @param res
	 */
	@RequestMapping(value="/addAttention", method=RequestMethod.POST)
	public void addAttention(int focusedId, HttpServletRequest req, HttpServletResponse res){
		User user = (User)req.getSession().getAttribute("user");
		boolean isSuccess = attentionService.addAttention(user.getId(), focusedId); 
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 取关
	 * @param followerId
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="/cancelAttention", method=RequestMethod.POST)
	public void cancelAttention(int focusedId, HttpServletRequest req, HttpServletResponse res){
		User user = (User)req.getSession().getAttribute("user");
		boolean isSuccess = attentionService.cancelAttention(user.getId(), focusedId);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 转到消息提醒列表页
	 * @param uid
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/person/notice")
	public String goToNoticeList(int page, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		if(page < 1)
			page = 1;
		Map<String,Object> map = noticeService.getNoticeList(user.getId(), page);
		if(map == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> userMap = (Map<String,Object>)map.get("userMap");
		req.setAttribute("u", userMap.get("u"));
		req.setAttribute("focusNum", userMap.get("focusNum"));
		req.setAttribute("fansNum", userMap.get("fansNum"));
		req.setAttribute("articleNum", userMap.get("articleNum"));
		req.setAttribute("isFocused", userMap.get("isFocused"));
		req.setAttribute("replies", map.get("replies"));
		req.setAttribute("noticeNum", map.get("noticeNum"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("currentPage", page);
		return "jsp/person_notice.jsp";
	}
	
	/**
	 * 转到用户动态页面
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/person/dynamic")
	public String goToDynamicList(int page, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		if(page < 1)
			page = 1;
		Map<String,Object> map = dynamicService.getDynamicList(user.getId(), page);
		if(map == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		@SuppressWarnings("unchecked")
		Map<String,Object> userMap = (Map<String,Object>)map.get("userMap");
		req.setAttribute("u", userMap.get("u"));
		req.setAttribute("focusNum", userMap.get("focusNum"));
		req.setAttribute("fansNum", userMap.get("fansNum"));
		req.setAttribute("articleNum", userMap.get("articleNum"));
		req.setAttribute("isFocused", userMap.get("isFocused"));
		req.setAttribute("dynamics", map.get("dynamics"));
		req.setAttribute("dynamicNum", map.get("dynamicNum"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("currentPage", page);
		return "jsp/person_dynamic.jsp";
	}

	//getters and setters
	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CollectService getCollectService() {
		return collectService;
	}
	
	@Resource(name="collectService")
	public void setCollectService(CollectService collectService) {
		this.collectService = collectService;
	}

	public AttentionService getAttentionService() {
		return attentionService;
	}
	
	@Resource(name="attentionService")
	public void setAttentionService(AttentionService attentionService) {
		this.attentionService = attentionService;
	}

	public NoticeService getNoticeService() {
		return noticeService;
	}
	
	@Resource(name="noticeService")
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public DynamicService getDynamicService() {
		return dynamicService;
	}
	
	@Resource(name="dynamicService")
	public void setDynamicService(DynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}

	public PropertiesLoader getConfig() {
		return config;
	}
	
	@Resource(name="config")
	public void setConfig(PropertiesLoader config) {
		this.config = config;
	}
	
}
