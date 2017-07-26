package blog.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import blog.dao.NoticeDao;
import blog.dao.mappers.ReplyMapper;
import blog.model.Reply;

/** 
 * NoticeDao实现类
 * @author zjz
 */
@Repository("noticeDao")
public class NoticeDaoImpl implements NoticeDao{
	private JdbcTemplate jt;
	
	/**
	 * @see blog.dao.NoticeDao#getNoticeList(int, int, int)
	 */
	public List<Reply> getNoticeList(int uid, int start, int num){
		String sql_1 = "select r.* from reply_notice rn join reply r on rn.reply_id=r.id where rn.writer_id=? order by r.replytime desc limit ?,?";
		List<Reply> replies = jt.query(sql_1, new Object[]{uid,start,num}, new ReplyMapper());
		return replies;
	}
	
	/**
	 * @see blog.dao.NoticeDao#getNoticeNumOfUser(int)
	 */
	public int getNoticeNumOfUser(int uid){
		String sql = "select count(id) from reply_notice where writer_id=?";
		return jt.queryForObject(sql, new Object[]{uid}, Integer.class);
	}
	
	/**
	 * @see blog.dao.NoticeDao#getUnreadNoticeNum(int)
	 */
	public int getUnreadNoticeNum(int uid){
		String sql = "select count(id) from reply_notice where writer_id=? and isread=0";
		return jt.queryForObject(sql, new Object[]{uid}, Integer.class);
	}
	
	/** 
	 * @see blog.dao.NoticeDao#updateUnreadNotice(int)
	 */
	public void updateUnreadNotice(int uid){
		String sql = "update reply_notice set isread=1 where writer_id=? and isread=0";
		jt.update(sql, new Object[]{uid});
	}
	
	//getters and setters
	public JdbcTemplate getJt() {
		return jt;
	}
	
	@Resource(name="jdbcTemplate")
	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}
}
