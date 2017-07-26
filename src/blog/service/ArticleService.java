package blog.service;

import java.util.List;
import java.util.Map;

import blog.model.Article;

/** 
 * 文章管理Service接口
 * @author zjz
 */
public interface ArticleService {
	
	/**
	 * 获取类别下文章列表及用户信息
	 * @param cid
	 * @param page
	 * @return 
	 */
	public List<Article> getArticleList(int cid, int page);
	
	/**
	 * 获取文章列表
	 * @param page
	 * @param searchTitle 按题目查询的字符串
	 * @return
	 */
	public Map<String,Object> getArticleList(int page, String searchTitle);
	
	/**
	 * 获取推荐列表
	 * @param page
	 * @return
	 */
	public Map<String,Object> getRecommendList(int page);
	
	/**
	 * 获取推荐列表
	 * @return
	 */
	public List<Article> getRecommendList();
	
	/**
	 * 获取类别信息及文章列表
	 * @param cid
	 * @param page
	 * @return
	 */
	public Map<String,Object> getCategoryAndArticleList(int cid, int page);
	
	/**
	 * 获取用户文章列表
	 * @param uid
	 * @param visitorId
	 * @param page
	 * @return
	 */
	public Map<String,Object> getArticleListOfUser(int uid, int visitorId, int page);
	
	/**
	 * 获取文章正文等信息，用于文章正文页的显示
	 * @param aid
	 * @param visitorId 访问者id，未登录则为0
	 * @return 
	 */
	public Map<String,Object> getArticle(int aid, int visitorId);
	
	/**
	 * 获取用户文章，用于文章编辑
	 * @param aid
	 * @param wid
	 * @return
	 */
	public Article getArticleOfUser(int aid, int wid);
	
	/**
	 * 添加新文章
	 * @param article
	 * @return 验证不通过，直接返回false
	 */
	public boolean addArticle(Article article);
	
	/**
	 * 更新文章
	 * @param article
	 * @return
	 */
	public boolean updateArticle(Article article);
	
	/**
	 * 删除文章
	 * @param aid
	 * @param wid 作者id
	 * @return
	 */
	public boolean deleteArticle(int aid, int wid);
	
	/**
	 * 删除文章
	 * @param aid
	 * @return
	 */
	public boolean deleteArticle(int aid);
	
	/**
	 * 获取热门文章列表
	 * @param cid
	 * @return
	 */
	public List<Article> getHotArticleList(int cid);
	
	/**
	 * 获取最新文章列表
	 * @return
	 */
	public List<Article> getNewArticleList();
	
	/**
	 * 在缓存中获取热门文章列表
	 * @param cid
	 * @return
	 */
	public List<Article> getHotArticleListInCache(int cid);
	
	/**
	 * 设为推荐
	 * @param aid
	 * @return
	 */
	public boolean addRecommend(int aid);
	
	/**
	 * 取消推荐
	 * @param aid
	 * @return
	 */
	public boolean deleteRecommend(int aid);
	
}
