package blog.interceptors;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import blog.model.Permission;
import blog.model.Role;
import blog.model.User;

/** 
 * 权限拦截
 * @author zjz
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter{

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		boolean flag = false;
		User user = (User)req.getSession().getAttribute("user");  //获取当前用户
		String url = req.getServletPath();
		flag = validatePermission(url, user);
		if(!flag){
			if(user == null){
				res.sendRedirect(req.getContextPath() + "/login");
			}else{
				return false;
			}
		}
		return flag;
	}
	
	/**
	 * 查看是否拥有权限
	 * @param url
	 * @param user
	 * @return
	 */
	private boolean validatePermission(String url, User user){
		if(user == null)
			return false;
		//转换为对象
		Permission inputUrl = new Permission();
		inputUrl.setUrl(url);
		//进行角色权限匹配
		List<Role> roles = user.getRoles();  //角色列表
		if(roles == null)
			return false;
		for(int i=0; i<roles.size(); i++){
			Role role = roles.get(i);
			Set<Permission> permissions = role.getPermissions();
			if(permissions.contains(inputUrl)){
				return true;
			}
		}
		return false;
	}
}
