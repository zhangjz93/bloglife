package blog.model;

import java.io.Serializable;

/** 
 * 角色权限的POJO类
 * @author zjz
 */
public class Permission implements Serializable{
	private static final long serialVersionUID = 5694253176503873879L;
	
	private int id;
	private String url;  //允许的URL
	
	public int getId() {
		return id;
	}
	public String getUrl() {
		return url;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public int hashCode() {
		return url.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Permission){
			Permission p = (Permission)obj;
			return p.url.equals(url);
 		}else{
 			return false;
 		}
	}
	@Override
	public String toString() {
		return url;
	}
}
