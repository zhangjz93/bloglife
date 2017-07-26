package blog.dao;

import java.util.List;

import blog.model.Article;
import blog.model.Content;

/** 
 * 文章管理DAO接口
 * @author zjz
 */
public interface ArticleDao {
	
	/**
	 * 获取文章列表及个人信息
	 * @param cid
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Article> getArticleList(int cid, int start, int num);
	
	/**
	 * 获取文章列表
	 * @param start
	 * @param num
	 * @param searchTitle 按题目查询的字符串
	 * @return
	 */
	public List<Article> getArticleList(int start, int num, String searchTitle);
		
	/**
	 * 获取推荐列表
	 * @return
	 */
	public List<Article> getRecommendList(int start, int num);
	
	/**
	 * 获取用户文章列表及所属类别信息
	 * @param uid
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Article> getArticleListOfUser(int uid, int start, int num);
	
	/**
	 * 获取文章信息
	 * @param aid
	 * @return
	 */
	public Article getArticle(int aid);
	
	/**
	 * 获取文章信息
	 * @param aid
	 * @param wid 作者id
	 * @return
	 */
	public Article getArticle(int aid, int wid);
	
	/**
	 * 获取正文
	 * @param aid
	 * @return
	 */
	public Content getContent(int aid);

	/**
	 * 获取某类别总文章数量
	 * @param cid
	 * @return
	 */
	public int getArticleNum(int cid);
	
	/**
	 * 获取总文章数量
	 * @param searchTitle 查询的字符串
	 * @return
	 */
	public int getArticleNum(String searchTitle);
	
	/**
	 * 获取推荐文章数量
	 * @return
	 */
	public int getRecommendNum();
	
	/**
	 * 获取回复数量
	 * @param aid
	 * @return
	 */
	public int getReplyNum(int aid);
	
	/**
	 * 添加新文章
	 * @param article
	 * @return 新添加的文章id
	 */
	public int addArticle(final Article article);
	
	/**
	 * 更新文章
	 * @param article
	 */
	public void updateArticle(Article article);
	
	/**
	 * 删除文章
	 * @param aid
	 * @param wid 作者id
	 * @return 影响的行数
	 */
	public int deleteArticle(int aid, int wid);
	
	/**
	 * 删除文章
	 * @param aid
	 */
	public void deleteArticle(int aid);
	
	/**
	 * 增加文章点击量
	 * @param aid
	 * @param toplimit 点击量上限
	 */
	public void addClickNum(int aid, int toplimit);
	
	/**
	 * 获取某用户发表的文章数
	 * @param uid
	 * @return
	 */
	public int getArticleNumOfUser(int uid);
	
	/**
	 * 更新最后楼层
	 * @param aid
	 */
	public void updateLastCount(int aid);
	
	/**
	 * 获取热门文章
	 * @param cid 
	 * @param num 显示数量
	 * @return
	 */
	public List<Article> getHotArticleList(int cid, int num);
	
	/**
	 * 获取最新发布文章列表
	 * @param num
	 * @return
	 */
	public List<Article> getNewArticleList(int num);
	
	/**
	 * 设为推荐
	 * @param aid
	 */
	public void addRecommend(int aid);
	
	/**
	 * 取消推荐
	 * @param aid
	 */
	public void deleteRecommend(int aid);
	
}
