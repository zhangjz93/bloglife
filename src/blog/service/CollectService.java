package blog.service;

import java.util.Map;

/** 
 * 收藏管理Service层
 * @author zjz
 */
public interface CollectService {
	
	/**
	 * 收藏文章
	 * @param uid
	 * @param aid
	 * @return
	 */
	public boolean collectArticle(int uid, int aid);
	
	/**
	 * 取消文章收藏
	 * @param uid
	 * @param aid
	 * @return
	 */
	public boolean cancelCollect(int uid, int aid);
	
	/**
	 * 获取用户收藏列表
	 * @param uid
	 * @param visitorId 
	 * @param page
	 */
	public Map<String,Object> getCollectOfUser(int uid, int visitorId, int page);
}
