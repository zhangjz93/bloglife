package blog.service;

import java.util.List;

import blog.model.Permission;
import blog.model.Role;

/** 
 * 权限管理Service接口
 * @author zjz
 */
public interface PermissionService {
	
	/**
	 * 获取用户角色权限列表
	 * @param uid
	 * @return
	 */
	public List<Role> getRoleList(int uid);
	
	/**
	 * 获取所有角色权限列表
	 * @return
	 */
	public List<Role> getRoleList();
	
	/**
	 * 更新权限路径
	 * @param permission
	 * @return
	 */
	public boolean updatePermission(Permission permission);
	
	/**
	 * 删除权限
	 * @param pid
	 * @return
	 */
	public boolean deletePermission(int pid);
	
	/**
	 * 添加权限
	 * @param rid
	 * @param permission
	 * @return
	 */
	public boolean addPermission(int rid, Permission permission);
	
	/**
	 * 添加用户角色
	 * @param uid
	 * @param role 角色名称
	 * @return
	 */
	public boolean addRole(int uid, String role);
	
	/**
	 * 删除用户角色
	 * @param uid
	 * @param role
	 * @return
	 */
	public boolean deleteRole(int uid, String role);
	
}
