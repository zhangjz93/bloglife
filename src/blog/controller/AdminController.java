package blog.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.enumeration.ErrorEnum;
import blog.model.Category;
import blog.model.Permission;
import blog.model.Role;
import blog.service.ArticleService;
import blog.service.CategoryService;
import blog.service.PermissionService;
import blog.service.ReplyService;
import blog.service.UserService;

/** 
 * 后台管理Controller
 * @author zjz
 */
@Controller
public class AdminController extends BaseController{
	private CategoryService categoryService;
	private UserService userService;
	private ArticleService articleService;
	private PermissionService permissionService;
	private ReplyService replyService;
	
	@RequestMapping("/admin/index")
	public String goToIndex(){
		return "jsp/admin/index.jsp";
	}
	
	/**
	 * 转到类别列表页面
	 * @return
	 */
	@RequestMapping("/admin/categoryList")
	public String goToCategoryList(HttpServletRequest req){
		List<Category> categories = categoryService.getCategoryList();
		req.setAttribute("categories", categories);
		return "jsp/admin/category_list.jsp";
	}
	
	/**
	 * 删除一个类别
	 * @param res
	 */
	@RequestMapping(value="/admin/deleteCategory", method=RequestMethod.POST)
	public void deleteCategory(int cid, HttpServletResponse res){
		boolean isSuccess = categoryService.deleteCategory(cid);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 转到类别添加页面
	 * @return
	 */
	@RequestMapping("/admin/categoryAdd")
	public String goToCategoryAdd(){
		return "jsp/admin/category_add.jsp";
	}
	
	/**
	 * 添加类别
	 * @param category
	 * @return
	 */
	@RequestMapping(value="/admin/addCategory", method=RequestMethod.POST)
	public String addCategory(Category category, HttpServletRequest req){
		boolean isSuccess = categoryService.addCategory(category);
		if(!isSuccess){
			return jumpToErrorPage(req, ErrorEnum.FORMAT);
		}
		String redir = "redirect: " + req.getContextPath() + "/admin/categoryList";
		return redir;
	}
	
	/**
	 * 更新类别
	 * @param category
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/admin/updateCategory", method=RequestMethod.POST)
	public String updateCategory(Category category, HttpServletRequest req){
		boolean isSuccess = categoryService.updateCategory(category);
		if(!isSuccess){
			return jumpToErrorPage(req, ErrorEnum.FORMAT);
		}
		req.setAttribute("resultMsg", "类别更新成功！");
		return "jsp/admin/result.jsp";
	}
	
	/**
	 * 转到用户列表页面
	 * @param page
	 * @param search
	 * @return
	 */
	@RequestMapping("/admin/userList")
	public String goToUserList(int page, String search, HttpServletRequest req){
		if(page < 1)
			page = 1;
		Map<String,Object> map = userService.getUserList(page, search);
		req.setAttribute("users", map.get("users"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("userNum", map.get("userNum"));
		req.setAttribute("currentPage", page);
		req.setAttribute("search", map.get("search"));
		return "jsp/admin/user_list.jsp";
	}
	
	/**
	 * 删除一个用户
	 * @param uid
	 * @param res
	 */
	@RequestMapping(value="/admin/deleteUser", method=RequestMethod.POST)
	public void deleteUser(int uid, HttpServletResponse res){
		boolean isSuccess = userService.deleteUser(uid);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 添加用户角色
	 * @param uid
	 * @param rid
	 * @param res
	 */
	@RequestMapping(value="/admin/addRole", method=RequestMethod.POST)
	public void addRole(int uid, String role, HttpServletResponse res){
		boolean isSuccess = permissionService.addRole(uid, role);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 删除用户角色
	 * @param uid
	 * @param rid
	 * @param res
	 */
	@RequestMapping(value="/admin/deleteRole", method=RequestMethod.POST)
	public void deleteRole(int uid, String role, HttpServletResponse res){
		boolean isSuccess = permissionService.deleteRole(uid, role);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 转到文章列表页面
	 * @param page
	 * @param search
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/articleList")
	public String goToArticleList(int page, String search, HttpServletRequest req){
		if(page < 1)
			page = 1;
		Map<String,Object> map = articleService.getArticleList(page, search);
		req.setAttribute("articles", map.get("articles"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("articleNum", map.get("articleNum"));
		req.setAttribute("currentPage", page);
		req.setAttribute("search", map.get("search"));
		return "jsp/admin/article_list.jsp";
	}
	
	/**
	 * 转到推荐文章列表页面
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/recommendList")
	public String goToRecommendList(int page, HttpServletRequest req){
		if(page < 1)
			page = 1;
		Map<String,Object> map = articleService.getRecommendList(page);
		req.setAttribute("articles", map.get("articles"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("articleNum", map.get("articleNum"));
		req.setAttribute("currentPage", page);
		return "jsp/admin/good_list.jsp";
	}
	
	/**
	 * 删除文章
	 * @param aid
	 * @param res
	 */
	@RequestMapping(value="/admin/deleteArticle", method=RequestMethod.POST)
	public void deleteArticle(int aid, HttpServletResponse res){
		boolean isSuccess = articleService.deleteArticle(aid);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 设为推荐
	 * @param aid
	 * @param res
	 */
	@RequestMapping(value="/admin/addRecommend", method=RequestMethod.POST)
	public void addRecommend(int aid, HttpServletResponse res){
		boolean isSuccess = articleService.addRecommend(aid);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 取消推荐
	 * @param aid
	 * @param res
	 */
	@RequestMapping(value="/admin/deleteRecommend", method=RequestMethod.POST)
	public void deleteRecommend(int aid, HttpServletResponse res){
		boolean isSuccess = articleService.deleteRecommend(aid);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 获取文章下的回复列表
	 * @param aid
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/replyList")
	public String getReplyList(int aid, int page, HttpServletRequest req){
		if(page < 1)
			page = 1;
		Map<String,Object> map = replyService.getReplyList(aid, page, true);
		req.setAttribute("replies", map.get("replies"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("replyNum", map.get("replyNum"));
		req.setAttribute("articleInfo", map.get("articleInfo"));
		req.setAttribute("currentPage", page);
		return "jsp/admin/reply_list.jsp";
	}
	
	/**
	 * 删除回复
	 * @param rid
	 * @param res
	 */
	@RequestMapping(value="/admin/deleteReply", method=RequestMethod.POST)
	public void deleteReply(int rid, HttpServletResponse res){
		boolean isSuccess = replyService.deleteReply(rid);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 转到权限列表页面
	 * @param req
	 * @return
	 */
	@RequestMapping("/admin/permissionList")
	public String goToPermissionList(HttpServletRequest req){
		List<Role> roles = permissionService.getRoleList();
		req.setAttribute("roles", roles);
		return "jsp/admin/permission_list.jsp";
	}
	
	/**
	 * 更新权限URL
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/admin/updatePermission", method=RequestMethod.POST)
	public String updatePermission(Permission permission, HttpServletRequest req){
		permissionService.updatePermission(permission);
		return "redirect: " + req.getContextPath() + "/admin/permissionList";
	}
	
	/**
	 * 删除权限
	 * @param pid
	 * @param res
	 */
	@RequestMapping(value="/admin/deletePermission", method=RequestMethod.POST)
	public void deletePermission(int pid, HttpServletResponse res){
		boolean isSuccess = permissionService.deletePermission(pid);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 添加权限
	 * @param rid
	 * @param url
	 * @return
	 */
	@RequestMapping(value="/admin/addPermission", method=RequestMethod.POST)
	public String addPermission(int rid, Permission permission, HttpServletRequest req){
		permissionService.addPermission(rid, permission);
		return "redirect: " + req.getContextPath() + "/admin/permissionList";
	}
	
	//getters and setters
	public CategoryService getCategoryService() {
		return categoryService;
	}
	
	@Resource(name="categoryService")
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}
	
	@Resource(name="permissionService")
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}
	
	@Resource(name="articleService")
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public ReplyService getReplyService() {
		return replyService;
	}
	
	@Resource(name="replyService")
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}
}
