package blog.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.ArticleDao;
import blog.dao.AttentionDao;
import blog.dao.UserDao;
import blog.enumeration.ValueConstraint;
import blog.model.User;
import blog.service.PermissionService;
import blog.service.UserService;
import blog.util.PageUtil;
import blog.util.PropertiesLoader;
import blog.util.TextUtil;
import blog.util.ValidationUtil;

/**
 * UserService接口实现
 * @author zjz
 */
@Service("userService")
public class UserServiceImpl implements UserService{
	private UserDao userDao;
	private ArticleDao articleDao;
	private AttentionDao attentionDao;
	private PermissionService permissionService;
	private PropertiesLoader config;
	
	/**
	 * @see blog.service.UserService#login(java.lang.String)
	 */
	@Transactional
	public User login(User user){
		User u = userDao.getUser(user.getName());
		if(u == null){
			u = new User();
			u.setName(user.getName());
			u.setPhoto(user.getPhoto());
			u.setMaster(false);
			u.setRegistTime(new Date(System.currentTimeMillis()));  //注册时间
			u.setIntro("未填写");
			u.setCareer("未填写");
			u.setHobby("未填写");
			u.setSkill("未填写");
			int key = userDao.addUser(u);
			u.setId(key);
			//添加角色
			permissionService.addRole(key, "user"); //默认为普通用户
		}
		u.setRoles(permissionService.getRoleList(u.getId()));  //获取角色
		return u;
	}
	
	/**
	 * @see blog.service.UserService#getUserInfo(int, int)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getUserInfo(int uid, int visitorId){
		Map<String,Object> map = new HashMap<String,Object>();
		User u = userDao.getUserById(uid);
		if(u == null)
			return null;
		int focusNum = attentionDao.getAttentionNum(uid, true);
		int fansNum = attentionDao.getAttentionNum(uid, false);
		int articleNum = articleDao.getArticleNumOfUser(uid);
		boolean isFocused = false;
		if(visitorId != 0){
			isFocused = attentionDao.isFocusedByUser(uid, visitorId);
		}
		map.put("u", u);
		map.put("focusNum", focusNum);
		map.put("fansNum", fansNum);
		map.put("articleNum", articleNum);
		map.put("isFocused", isFocused);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public String updateUser(User user){
		user.setCareer(user.getCareer().trim());
		user.setHobby(user.getHobby().trim());
		user.setSkill(user.getSkill().trim());
		user.setIntro(user.getIntro().trim());
		JSONObject json = new JSONObject();
		if(!userInfoValidation(user)){
			json.put("flag", 1);
			return json.toJSONString();  //验证不通过
		}
		if(user.getCareer().length() == 0)
			user.setCareer("未填写");
		if(user.getHobby().length() == 0)
			user.setHobby("未填写");
		if(user.getSkill().length() == 0)
			user.setSkill("未填写");
		if(user.getIntro().length() == 0)
			user.setIntro("未填写");
		userDao.updateUser(user);
		json.put("flag", 0);
		json.put("career", user.getCareer());
		json.put("hobby", user.getHobby());
		json.put("skill", user.getSkill());
		json.put("intro", user.getIntro());
		return json.toJSONString();  
	}
	
	/**
	 * @see blog.service.UserService#getUserList(int, java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getUserList(int page, String searchName){
		String utf8Str = "".equals(searchName) ? "" : TextUtil.encodingStr(searchName, "UTF-8");//转码
		Map<String,Object> map = new HashMap<String,Object>();
		int pageSize = config.getInt("user.pageSize");
		int start = (page-1) * pageSize;
		List<User> users = userDao.getUserList(start, pageSize, utf8Str);
		for(int i=0; i<users.size(); i++){
			User user = users.get(i);
			user.setRoles(permissionService.getRoleList(user.getId()));  //用户角色
		}
		int userNum = userDao.getUserNum(utf8Str);
		int totalPage = PageUtil.getTotalPage(userNum, pageSize);
		map.put("users", users);
		map.put("totalPage", totalPage);
		map.put("userNum", userNum);
		map.put("search", utf8Str);
		return map;
	}
	
	/**
	 * @see blog.service.UserService#deleteUser(int)
	 */
	@Transactional
	public boolean deleteUser(int uid){
		userDao.deleteUser(uid);
		return true;
	}
	
	/**
	 * 个人信息编辑表单验证
	 * @param personInfoDto
	 * @return
	 */
	private boolean userInfoValidation(User user){
		return ValidationUtil.isLengthOK(user.getCareer(), 0, ValueConstraint.USER_CARRER) && 
				ValidationUtil.isLengthOK(user.getHobby(), 0, ValueConstraint.USER_HOBBY) && 
				ValidationUtil.isLengthOK(user.getSkill(), 0, ValueConstraint.USER_SKILL) && 
				ValidationUtil.isLengthOK(user.getIntro(), 0, ValueConstraint.USER_INTRO);
	}
	
	//getters and setters
	public UserDao getUserDao() {
		return userDao;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}
	
	@Resource(name="articleDao")
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public AttentionDao getAttentionDao() {
		return attentionDao;
	}
	
	@Resource(name="attentionDao")
	public void setAttentionDao(AttentionDao attentionDao) {
		this.attentionDao = attentionDao;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}
	
	@Resource(name="permissionService")
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public PropertiesLoader getConfig() {
		return config;
	}
	
	@Resource(name="config")
	public void setConfig(PropertiesLoader config) {
		this.config = config;
	}
	
}
