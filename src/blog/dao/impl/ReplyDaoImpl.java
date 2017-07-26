package blog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import blog.dao.ReplyDao;
import blog.dao.mappers.ReplyMapper;
import blog.model.Reply;

/** 
 * ReplyDao实现类
 * @author zjz
 */
@Repository("replyDao")
public class ReplyDaoImpl implements ReplyDao {
	private JdbcTemplate jt;
	
	/**
	 * @see blog.dao.ReplyDao#getReplies(int, int, int)
	 */
	public List<Reply> getReplies(int aid, int start, int num, boolean asc){
		String sql = "select * from reply where article_id=? order by replytime ";
		if(!asc)
			sql += "desc ";
		sql += "limit ?,?";
		List<Reply> replies = jt.query(sql, new Object[]{aid,start,num}, new ReplyMapper());
		return replies;
	}
	
	/**
	 * @see blog.dao.ReplyDao#getReply(int)
	 */
	public Reply getReply(int rid){
		String sql = "select * from reply where id=?";
		List<Reply> replies = jt.query(sql, new Object[]{rid}, new ReplyMapper());
		return replies.size()==0 ? null : replies.get(0);
	}
	
	/**
	 * @see blog.dao.ReplyDao#getReplyNum(int)
	 */
	public int getReplyNum(int aid){
		String sql = "select count(id) from reply where article_id=?";
		return jt.queryForObject(sql, new Object[]{aid}, Integer.class);
	}
	
	/**
	 * @see blog.dao.ReplyDao#addReply(blog.model.Reply)
	 */
	public int addReply(final Reply reply){
		final String sql = "insert into reply values(null,?,?,?,?,?,?)";
		KeyHolder kh = new GeneratedKeyHolder();
		jt.update(new PreparedStatementCreator(){
			//override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, reply.getWriter().getId());
				ps.setInt(2, reply.getArticle().getId());
				ps.setInt(3, reply.getParent().getId());
				ps.setString(4, reply.getWords());
				ps.setInt(5, reply.getCount());
				ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				return ps;
			}
		}, kh);
		return kh.getKey().intValue();
	}
	
	/**
	 * @see blog.dao.ReplyDao#isReplyExist(int)
	 */
	public boolean isReplyExist(int rid){
		String sql = "select count(id) from reply where id=?";
		int count = jt.queryForObject(sql, new Object[]{rid}, Integer.class);
		return count==0 ? false : true;
	}
	
	/**
	 * @see blog.dao.ReplyDao#deleteReply(int, int)
	 */
	public int deleteReply(int rid, int wid){
		String sql = "delete from reply where id=? and writer_id=?";
		return jt.update(sql, new Object[]{rid,wid});
	}
	
	/**
	 * @see blog.dao.ReplyDao#deleteReply(int)
	 */
	public void deleteReply(int rid){
		String sql = "delete from reply where id=?";
		jt.update(sql, new Object[]{rid});
	}
	
	/**
	 * @see blog.dao.ReplyDao#addNotice(int, int)
	 */
	public void addNotice(int uid, int rid){
		String sql = "insert into reply_notice values(null,?,?,0)";
		jt.update(sql, new Object[]{uid,rid});
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
