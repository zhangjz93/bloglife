package blog.dao;

import java.util.List;

import blog.model.Article;

/** 
 * 收藏管理DAO层
 * @author zjz
 */
public interface CollectDao {
	
	/**
	 * 添加文章收藏记录
	 * @param uid 收藏者id
	 * @param aid 收藏文章的id
	 */
	public void addCollect(int uid, int aid);
	
	/**
	 * 删除文章收藏
	 * @param uid
	 * @param aid
	 * @return 操作所影响的行数
	 */
	public int deleteCollect(int uid, int aid);
	
	/**
	 * 查询某用户是否收藏某篇文章
	 * @param uid
	 * @param aid
	 * @return
	 */
	public boolean isUserCollect(int uid, int aid);
	
	/**
	 * 获取用户收藏列表
	 * @param uid
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Article> getCollectListOfUser(int uid, int start, int num);
	
	/**
	 * 获取用户收藏总数量
	 * @param uid
	 * @return
	 */
	public int getCollectNumOfUser(int uid);
}
