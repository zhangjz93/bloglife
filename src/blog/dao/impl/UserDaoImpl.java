package blog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import blog.dao.UserDao;
import blog.dao.mappers.UserMapper;
import blog.model.User;

/**
 * UserDao接口实现
 * @author zjz
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao{
	private JdbcTemplate jt;
	
	/**
	 * 添加用户
	 * @see blog.dao.UserDao#addUser(blog.model.User)
	 */
	public int addUser(final User user) {
		final String sql = "insert into user values(null,?,?,?,?,?,?,?,?)";
		KeyHolder kh = new GeneratedKeyHolder();
		jt.update(new PreparedStatementCreator(){
			//override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, user.getName());
				ps.setString(2, user.getIntro());
				ps.setString(3, user.getPhoto());
				ps.setDate(4, user.getRegistTime());
				ps.setBoolean(5, user.isMaster());
				ps.setString(6, user.getCareer());
				ps.setString(7, user.getHobby());
				ps.setString(8, user.getSkill());
				return ps;
			}
			
		}, kh);
		return kh.getKey().intValue();  //返回id
	}
	
	/**
	 * @see blog.dao.UserDao#updateUser(blog.model.User)
	 */
	public void updateUser(User user){
		String sql = "update user set career=?,hobby=?,skill=?,intro=? where id=?";
		jt.update(sql, new Object[]{user.getCareer(),user.getHobby(),user.getSkill(),user.getIntro(),user.getId()});
	}
	
	/**
	 * 按用户名查找用户
	 * @see blog.dao.UserDao#getUser(java.lang.String, java.lang.String)
	 */
	public User getUser(String name){
		String sql = "select * from user where name=?";
		List<User> users = jt.query(sql, new Object[]{name}, new UserMapper());
		return users.size()==0 ? null : users.get(0);
	}
	
	/**
	 * @see blog.dao.UserDao#getUserById(int)
	 */
	public User getUserById(int uid){
		String sql = "select * from user where id=?";
		List<User> users = jt.query(sql, new Object[]{uid}, new UserMapper());
		return users.size()==0 ? null : users.get(0);
	}
	
	/**
	 * @see blog.dao.UserDao#getUserList(int, int, java.lang.String)
	 */
	public List<User> getUserList(int start, int num, String searchName){
		String sql = "select * from user where name like '" + searchName + "%' limit ?,?";
		List<User> users = jt.query(sql, new Object[]{start,num}, new UserMapper());
		return users;
	}
	
	/**
	 * @see blog.dao.UserDao#getUserNum(String)
	 */
	public int getUserNum(String searchName){
		String sql = "select count(id) from user where name like '" + searchName + "%'";
		return jt.queryForObject(sql, Integer.class);
	}
	
	/**
	 * @see blog.dao.UserDao#deleteUser(int)
	 */
	public void deleteUser(int uid){
		String sql = "delete from user where id=?";
		jt.update(sql, new Object[]{uid});
	}
	
	/**
	 * @see blog.dao.UserDao#updatePhoto(int, java.lang.String)
	 */
	public void updatePhoto(int uid, String path){
		String sql = "update user set photo=? where id=?";
		jt.update(sql, new Object[]{path,uid});
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
