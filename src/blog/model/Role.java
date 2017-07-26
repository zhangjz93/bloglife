package blog.model;

import java.io.Serializable;
import java.util.Set;

/** 
 * 用户角色的POJO类
 * @author zjz
 */
public class Role implements Serializable{
	private static final long serialVersionUID = -628819754478853572L;
	
	private int id;
	private String name;
	private Set<Permission> permissions;  //该角色拥有的权限集合
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}
