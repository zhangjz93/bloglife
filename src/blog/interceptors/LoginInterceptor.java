package blog.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import blog.model.User;
import blog.service.UserService;
import blog.util.CookieUtil;

/** 
 * 自动登录拦截
 * @author zjz
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private UserService userService;

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		User user = (User)req.getSession().getAttribute("user");
		if(user == null){
			Cookie cookie = CookieUtil.getCookie("user", req);
			if(cookie != null){
				User u = new User();
				u.setName(cookie.getValue());
				User loginUser = userService.login(u);
				req.getSession().setAttribute("user", loginUser);
				return true;
			}
		}
		return true;
	}
	
	//getters and setters
	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
