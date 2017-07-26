package blog.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import blog.dao.AttentionDao;
import blog.dao.mappers.UserMapper;
import blog.model.User;

/** 
 * AttentionDao实现类
 * @author zjz
 */
@Repository("attentionDao")
public class AttentionDaoImpl implements AttentionDao {
	private JdbcTemplate jt;
	
	/**
	 * @see blog.dao.AttentionDao#addAttention(int, int)
	 */
	public void addAttention(int uid, int focused){
		String sql = "insert ignore into attention values(?,?,?)";
		jt.update(sql, new Object[]{uid,focused,new Timestamp(System.currentTimeMillis())});
	}
	
	/**
	 * @see blog.dao.AttentionDao#cancelAttention(int, int)
	 */
	public void cancelAttention(int uid, int focusedId){
		String sql = "delete from attention where focus_id=? and focused_id=?";
		jt.update(sql, new Object[]{uid,focusedId});
	}
	
	/**
	 * @see blog.dao.AttentionDao#getAttentionList(int, boolean, int, int)
	 */
	public List<User> getAttentionList(final int uid, final boolean isFocus, int start, int num){
		String sql = null;
		if(isFocus)
			sql = "select u.* from attention att join user u on att.focused_id=u.id where att.focus_id=? order by att.focustime desc limit ?,?";
		else
			sql = "select u.* from attention att join user u on att.focus_id=u.id where att.focused_id=? order by att.focustime desc limit ?,?";
		List<User> users = jt.query(sql, new Object[]{uid,start,num}, new UserMapper());
		return users;
	}
	
	/**
	 * @see blog.dao.AttentionDao#getAttentionNum(int, boolean)
	 */
	public int getAttentionNum(int uid, boolean isFocus){
		String sql = null;
		if(isFocus)
			sql = "select count(focus_id) from attention where focus_id=?";
		else
			sql = "select count(focus_id) from attention where focused_id=?";
		return jt.queryForObject(sql, new Object[]{uid}, Integer.class);
	}
	
	/**
	 * @see blog.dao.AttentionDao#isFocusedByUser(int, int)
	 */
	public boolean isFocusedByUser(int selfId, int otherId){
		String sql = "select count(focus_id) from attention where focus_id=? and focused_id=?";
		int count = jt.queryForObject(sql, new Object[]{otherId,selfId}, Integer.class);
		return count==0 ? false : true;
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
