package blog.service;

import java.util.Map;

/** 
 * 通知管理Service接口
 * @author zjz
 */
public interface NoticeService {
	
	/**
	 * 获取通知列表
	 * @param uid
	 * @param page
	 * @return
	 */
	public Map<String,Object> getNoticeList(int uid, int page);
	
	/**
	 * 获取未读通知数
	 * @param uid
	 * @return
	 */
	public int getUnreadNoticeNum(int uid);
	
}
