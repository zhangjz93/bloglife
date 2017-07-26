package blog.dao;

import java.util.List;

import blog.model.Permission;
import blog.model.Role;

/** 
 * 权限管理DAO接口
 * @author zjz
 */
public interface PermissionDao {
	
	/**
	 * 获取用户角色列表
	 * @param uid
	 * @return
	 */
	public List<Role> getRoleList(int uid);
	
	/**
	 * 获取所有角色及权限列表
	 * @return
	 */
	public List<Role> getRoleList();
	
	/**
	 * 获取角色权限列表
	 * @param rid
	 * @return
	 */
	public List<Permission> getPermissionList(int rid);
	
	/**
	 * 获取所有权限列表
	 * @return
	 */
	public List<Permission> getPermissionList();
	
	/**
	 * 更新权限路径
	 * @param permission
	 */
	public void updatePermission(Permission permission);
	
	/**
	 * 删除权限
	 * @param pid
	 */
	public void deletePermission(int pid);
	
	/**
	 * 添加权限
	 * @param rid
	 * @param permission
	 * @return 返回插入id
	 */
	public void addPermission(int rid, final Permission permission);
	
	/**
	 * 获取该权限所属角色信息
	 * @param pid
	 * @return
	 */
	public Role getRole(int pid);
	
	/**
	 * 获取角色名称对应id
	 * @param role
	 * @return
	 */
	public int getRoleId(String role);
	
	/**
	 * 添加用户角色
	 * @param uid
	 * @param role 角色名称
	 */
	public void addRole(int uid, String role);
	
	/**
	 * 删除用户角色
	 * @param uid
	 * @param role
	 */
	public void deleteRole(int uid, String role);
}
