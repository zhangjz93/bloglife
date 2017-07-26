package blog.dao;

import java.util.List;

import blog.model.Reply;

/** 
 * 通知管理DAO接口
 * @author zjz
 */
public interface NoticeDao {
	
	/**
	 * 获取通知列表
	 * @param uid
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Reply> getNoticeList(int uid, int start, int num);
	
	/**
	 * 获取通知总数量
	 * @param uid
	 * @return
	 */
	public int getNoticeNumOfUser(int uid);
	
	/**
	 * 获取未读通知数量
	 * @param uid
	 * @return
	 */
	public int getUnreadNoticeNum(int uid);
	
	/**
	 * 更新通知为已读
	 * @param uid
	 */
	public void updateUnreadNotice(int uid);
}
