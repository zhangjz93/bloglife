package blog.dao;

import java.util.List;

import blog.model.Reply;

/** 
 * 回复管理DAO层
 * @author zjz
 */
public interface ReplyDao {
	
	/**
	 * 获取回复列表，内部嵌套查询
	 * @param aid
	 * @param start
	 * @param num
	 * @param asc 是否按时间正序排列
	 * @return
	 */
	public List<Reply> getReplies(int aid, int start, int num, boolean asc);
	
	/**
	 * 获取回复
	 * @param rid
	 * @return
	 */
	public Reply getReply(int rid);
	
	/**
	 * 获取文章总回复数
	 * @param aid
	 * @return
	 */
	public int getReplyNum(int aid);
	
	/**
	 * 添加回复
	 * @param reply
	 * return 返回插入的id
	 */
	public int addReply(final Reply reply);
	
	/**
	 * 验证回复是否存在
	 * @param rid
	 * @return
	 */
	public boolean isReplyExist(int rid);
	
	/**
	 * 删除回复
	 * @param rid
	 * @param wid
	 * @return 影响的行数
	 */
	public int deleteReply(int rid, int wid);
	
	/**
	 * 删除回复
	 * @param rid
	 */
	public void deleteReply(int rid);
	
	/**
	 * 添加一条回复消息提醒
	 * @param uid 需要被提醒的用户id
	 * @param rid 新回复id
	 */
	public void addNotice(int uid, int rid);
}
