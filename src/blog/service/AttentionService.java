package blog.service;

import java.util.Map;

/** 
 * 关注信息管理Service层
 * @author zjz
 */
public interface AttentionService {
	
	/**
	 * 添加关注
	 * @param uid
	 * @param focused
	 * @return
	 */
	public boolean addAttention(int uid, int focused);
	
	/**
	 * 取关
	 * @param uid 关注者
	 * @param focusedId 被关注者
	 * @return
	 */
	public boolean cancelAttention(int uid, int focusedId);
	
	/**
	 * 获取关注列表
	 * @param uid
	 * @param visitorId
	 * @param page
	 * @param isFocus 是否获取关注列表
	 * @return
	 */
	public Map<String,Object> getFocusList(int uid, int visitorId, int page, boolean isFocus);
}
