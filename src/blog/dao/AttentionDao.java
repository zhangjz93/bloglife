package blog.dao;

import java.util.List;

import blog.model.User;

/** 
 * 关注管理DAO接口
 * @author zjz
 */
public interface AttentionDao {
	
	/**
	 * 添加关注
	 * @param uid
	 * @param focused 被关注用户id
	 */
	public void addAttention(int uid, int focused);
	
	/**
	 * 取关
	 * @param uid
	 * @param focusedId
	 */
	public void cancelAttention(int uid, int focusedId);
	
	/**
	 * 获取关注或被关注列表
	 * @param uid
	 * @param isFocus 是否获取关注列表
	 * @param start
	 * @param num
	 * @return
	 */
	public List<User> getAttentionList(final int uid, final boolean isFocus, int start, int num);
	
	/**
	 * 获取关注者或被关注者数量
	 * @param uid
	 * @param isFocus 获取列表类别，true为关注列表，false为粉丝列表
	 * @return
	 */
	public int getAttentionNum(int uid, boolean isFocus);
	
	/**
	 * 查看是否被某用户关注
	 * @param selfId
	 * @param otherId
	 * @return
	 */
	public boolean isFocusedByUser(int selfId, int otherId);
}
