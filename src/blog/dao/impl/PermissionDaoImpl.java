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

import blog.dao.PermissionDao;
import blog.dao.mappers.PermissionMapper;
import blog.dao.mappers.RoleMapper;
import blog.model.Permission;
import blog.model.Role;

/** 
 * PermissionDao实现类
 * @author zjz
 */
@Repository("permissionDao")
public class PermissionDaoImpl implements PermissionDao {
	private JdbcTemplate jt;
	
	/**
	 * @see blog.dao.PermissionDao#getRoleList(int)
	 */
	public List<Role> getRoleList(int uid){
		String sql = "select r.* from user_role ur join role r on ur.role_id=r.id where ur.user_id=?";
		List<Role> roles = jt.query(sql, new Object[]{uid}, new RoleMapper());
		return roles;
	}
	
	/**
	 * @see blog.dao.PermissionDao#getRoleList()
	 */
	public List<Role> getRoleList(){
		String sql = "select * from role";
		List<Role> roles = jt.query(sql, new RoleMapper());
		return roles;
	}
	
	/**
	 * @see blog.dao.PermissionDao#getPermissionList(int)
	 */
	public List<Permission> getPermissionList(int rid){
		String sql = "select p.* from role_permission rp join permission p on rp.permission_id=p.id where rp.role_id=?";
		List<Permission> permissions = jt.query(sql, new Object[]{rid}, new PermissionMapper());
		return permissions;
	}
	
	/**
	 * @see blog.dao.PermissionDao#getPermissionList()
	 */
	public List<Permission> getPermissionList(){
		String sql = "select p.* from role_permission rp join permission p on rp.permission_id=p.id";
		List<Permission> permissions = jt.query(sql, new PermissionMapper());
		return permissions;
	}
	
	/**
	 * @see blog.dao.PermissionDao#updatePermission(blog.model.Permission)
	 */
	public void updatePermission(Permission permission){
		String sql = "update permission set url=? where id=?";
		jt.update(sql, new Object[]{permission.getUrl(),permission.getId()});
	}
	
	/**
	 * @see blog.dao.PermissionDao#deletePermission(int)
	 */
	public void deletePermission(int pid){
		String sql = "delete from permission where id=?";
		jt.update(sql, new Object[]{pid});
	}
	
	/**
	 * @see blog.dao.PermissionDao#addPermission(int, blog.model.Permission)
	 */
	public void addPermission(int rid, final Permission permission){
		final String sql_1 = "insert into permission values (null,?)";
		final String sql_2 = "insert ignore into role_permission values(?,?)";
		KeyHolder kh = new GeneratedKeyHolder();
		jt.update(new PreparedStatementCreator(){
			//override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql_1);
				ps.setString(1, permission.getUrl());
				return ps;
			}
		}, kh);
		int key = kh.getKey().intValue();
		jt.update(sql_2, new Object[]{rid, key});
	}
	
	/**
	 * @see blog.dao.PermissionDao#getRole(int)
	 */
	public Role getRole(int pid){
		String sql = "select r.* from role_permission rp join role r on rp.role_id=r.id where rp.permission_id=?";
		return jt.queryForObject(sql, new Object[]{pid}, new RoleMapper());
	}
	
	/**
	 * @see blog.dao.PermissionDao#getRoleId(java.lang.String)
	 */
	public int getRoleId(String role){
		String sql = "select id from role where name=?";
		return jt.queryForObject(sql, new Object[]{role}, Integer.class);
	}
	
	/**
	 * @see blog.dao.PermissionDao#addRole(int, java.lang.String)
	 */
	public void addRole(int uid, String role){
		int rid = getRoleId(role);
		String sql = "insert ignore into user_role values(?,?)";
		jt.update(sql, new Object[]{uid,rid});
	}
	
	/**
	 * @see blog.dao.PermissionDao#deleteRole(int, java.lang.String)
	 */
	public void deleteRole(int uid, String role){
		int rid = getRoleId(role);
		String sql = "delete from user_role where user_id=? and role_id=?";
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
