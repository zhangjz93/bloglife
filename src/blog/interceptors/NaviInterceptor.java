package blog.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import blog.model.User;
import blog.service.NoticeService;

/** 
 * 导航栏
 * @author zjz
 */
public class NaviInterceptor extends HandlerInterceptorAdapter{
	private NoticeService noticeService;

	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		User user = (User)req.getSession().getAttribute("user");
		int unreadNum = 0;
		if(user != null){
			unreadNum = noticeService.getUnreadNoticeNum(user.getId());
		}
		req.setAttribute("unreadNum", unreadNum);
		return true;
	}
	
	//getters and setters
	public NoticeService getNoticeService() {
		return noticeService;
	}
	
	@Resource(name="noticeService")
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
}
