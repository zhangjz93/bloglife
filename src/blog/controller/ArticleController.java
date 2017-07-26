package blog.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.enumeration.ErrorEnum;
import blog.model.Article;
import blog.model.Category;
import blog.model.User;
import blog.service.ArticleService;
import blog.service.CategoryService;
import blog.service.CollectService;

/** 
 * 关于文章的Controller类
 * @author zjz
 */
@Controller
public class ArticleController extends BaseController{
	private ArticleService articleService;
	private CategoryService categoryService;
	private CollectService collectService;
	
	/**
	 * 文章列表页
	 * @param cid 类别id
	 * @param page 页参数
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/articles/{cid}")
	public String goToArticleList(@PathVariable("cid")int cid, int page, HttpServletRequest req){
		if(page < 1)
			page = 1;
		Map<String,Object> map= articleService.getCategoryAndArticleList(cid, page);
		if(map == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		List<Article> articles = (List<Article>)map.get("articles"); //文章列表
		req.setAttribute("articles", articles);  	
		req.setAttribute("category", map.get("category"));  //类别信息
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("articleNum", map.get("articleNum"));
		req.setAttribute("hotArticles", map.get("hotArticles"));
		return "jsp/article_list.jsp";
	}
	
	/**
	 * 文章正文页
	 * @param aid
	 * @param req
	 * @return
	 */
	@RequestMapping("/content/{aid}")
	public String goToArticleContent(@PathVariable("aid")int aid, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");  //获取用户
		Map<String,Object> map = null;
		if(user != null)
			map = articleService.getArticle(aid, user.getId());
		else
			map = articleService.getArticle(aid, 0);
		if(map == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		req.setAttribute("article", map.get("article"));
		req.setAttribute("replies", map.get("replies"));
		req.setAttribute("replyNum", map.get("replyNum"));
		req.setAttribute("replyNumOfFirstPage", map.get("replyNumOfFirstPage"));
		req.setAttribute("isCollect", map.get("isCollect"));
		return "jsp/article_content.jsp";
	}
	
	/**
	 * 新文章编辑页面
	 * @param cid
	 * @param req
	 * @return
	 */
	@RequestMapping("/write")
	public String goToArticleWrite(int cid, HttpServletRequest req){
		Category category = categoryService.getCategory(cid);
		if(category == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		req.setAttribute("category", category);
		req.setAttribute("mode", "w");
		return "jsp/article_write.jsp";
	}
	
	/**
	 * 编辑文章
	 * @param aid
	 * @param req
	 * @return
	 */
	@RequestMapping("/edit")
	public String goToArticleEdit(int aid, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		Article article = articleService.getArticleOfUser(aid, user.getId());
		if(article == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		req.setAttribute("article", article);
		req.setAttribute("category", article.getCategory());
		req.setAttribute("mode", "e");
		return "jsp/article_write.jsp";
	}
	
	/**
	 * 添加新文章
	 * @param article
	 * @param req
	 * @return 返回至文章列表页面
	 */
	@RequestMapping(value="/addArticle", method=RequestMethod.POST)
	public String addArticle(Article article, HttpServletRequest req){
		String mode = req.getParameter("mode");
		User user = (User)req.getSession().getAttribute("user");
		article.setWriter(user);
		boolean isSuccess = false;
		if(mode!=null && "w".equals(mode))
			isSuccess = articleService.addArticle(article);
		else{
			isSuccess = articleService.updateArticle(article);
		}
		if(!isSuccess)
			return jumpToErrorPage(req, ErrorEnum.FORMAT);
		return "redirect: articles/" + article.getCategory().getId() + "?page=1";  //跳转文章列表页
	}
	
	/**
	 * 删除文章
	 * @param aid
	 * @param res
	 * @param req
	 */
	@RequestMapping(value="/deleteArticle", method=RequestMethod.POST)
	public void deleteArticle(int aid, HttpServletResponse res, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		boolean isSuccess = articleService.deleteArticle(aid, user.getId());
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 转到用户文章列表页面
	 * @param uid
	 * @param page
	 * @param req
	 * @return
	 */
	@RequestMapping("/person/articles/{uid}")
	public String getArticleListOfUser(@PathVariable("uid")int uid, int page, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		Map<String,Object> map = null;
		if(page < 1)
			page = 1;
		if(user != null)
			map = articleService.getArticleListOfUser(uid, user.getId(), page);
		else
			map = articleService.getArticleListOfUser(uid, 0, page);
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
		req.setAttribute("articleNumOfUser", map.get("articleNum"));
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("currentPage", page);
		return "jsp/person_articles.jsp";
	}
	
	/**
	 * 添加收藏
	 * @param aid
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="/collectArticle", method=RequestMethod.POST)
	public void collectArticle(int aid, HttpServletRequest req, HttpServletResponse res){
		User user = (User)req.getSession().getAttribute("user");
		boolean isSuccess = collectService.collectArticle(user.getId(), aid);
		writeMessage(isSuccess, res);
	}
	
	/**
	 * 取消收藏
	 * @param aid
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="/cancelCollect", method=RequestMethod.POST)
	public void cancelCollect(int aid, HttpServletRequest req, HttpServletResponse res){
		User user = (User)req.getSession().getAttribute("user");
		boolean isSuccess = collectService.cancelCollect(user.getId(), aid);
		writeMessage(isSuccess, res);
	}
	
	//getters and setters
	public ArticleService getArticleService() {
		return articleService;
	}
	
	@Resource(name="articleService")
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}
	
	@Resource(name="categoryService")
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public CollectService getCollectService() {
		return collectService;
	}
	
	@Resource(name="collectService")
	public void setCollectService(CollectService collectService) {
		this.collectService = collectService;
	}
}
