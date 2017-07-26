package blog.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.PermissionDao;
import blog.model.Permission;
import blog.model.Role;
import blog.service.PermissionService;

/** 
 * PermissionService实现类
 * @author zjz
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	private PermissionDao permissionDao;
	
	/**
	 * @see blog.service.PermissionService#getRoleList(int)
	 */
	@Transactional(readOnly=true)
	public List<Role> getRoleList(int uid){
		List<Role> roles = permissionDao.getRoleList(uid);
		for(int i=0; i<roles.size(); i++){
			Role role = roles.get(i);
			List<Permission> permissions = permissionDao.getPermissionList(role.getId());
			role.setPermissions(new HashSet<Permission>(permissions));
		}
		return roles;
	}
	
	/**
	 * @see blog.service.PermissionService#getRoleList()
	 */
	@Transactional(readOnly=true)
	public List<Role> getRoleList(){
		List<Role> roles = permissionDao.getRoleList();
		for(int i=0; i<roles.size(); i++){
			Role role = roles.get(i);
			List<Permission> permissions = permissionDao.getPermissionList(role.getId());
			role.setPermissions(new HashSet<Permission>(permissions));
		}
		return roles;
	}
	
	/**
	 * @see blog.service.PermissionService#updatePermission(blog.model.Permission)
	 */
	@Transactional
	public boolean updatePermission(Permission permission){
		permissionDao.updatePermission(permission);
		return true;
	}
	
	/**
	 * @see blog.service.PermissionService#deletePermission(int)
	 */
	@Transactional
	public boolean deletePermission(int pid){
		permissionDao.deletePermission(pid);
		return true;
	}
	
	/**
	 * @see blog.service.PermissionService#addPermission(int, blog.model.Permission)
	 */
	@Transactional
	public boolean addPermission(int rid, Permission permission){
		permissionDao.addPermission(rid, permission);
		return true;
	}
	
	/**
	 * @see blog.service.PermissionService#addRole(int, java.lang.String)
	 */
	@Transactional
	public boolean addRole(int uid, String role){
		permissionDao.addRole(uid, role);
		return true;
	}
	
	/**
	 * @see blog.service.PermissionService#deleteRole(int, java.lang.String)
	 */
	@Transactional
	public boolean deleteRole(int uid, String role){
		permissionDao.deleteRole(uid, role);
		return true;
	}
	
	//getters and setters
	public PermissionDao getPermissionDao() {
		return permissionDao;
	}
	
	@Resource(name="permissionDao")
	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}
}
