package blog.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Cookie操作辅助类
 * @author zjz
 */
public class CookieUtil {
	
	/**
	 * 添加Cookie
	 * @param key
	 * @param value
	 * @param path
	 * @param maxAge
	 * @param res
	 */
	public static void addCookie(String key, String value, String path, int maxAge, HttpServletResponse res){
		Cookie cookie = new Cookie(key, value);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		res.addCookie(cookie);
	}
	
	/**
	 * 通过名称获取Cookie
	 * @param name
	 * @param req
	 * @return
	 */
	public static Cookie getCookie(String name, HttpServletRequest req){
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(name)){
				return cookie;
			}
		}
		return null;
	}
	
	
}
