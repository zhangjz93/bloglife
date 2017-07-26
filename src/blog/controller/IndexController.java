package blog.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.service.IndexService;
import blog.service.UserService;

/**
 * 关于主页跳转的Controller
 * @author zjz
 */
@Controller
public class IndexController extends BaseController{
	private UserService userService;
	private IndexService indexService;
	
	/**
	 * 主页
	 * @return
	 */
	@RequestMapping("/index")
	public String goToIndex(HttpServletRequest req, HttpServletResponse res){
		Map<String,Object> map = indexService.getIndexInfo();
		req.setAttribute("categories", map.get("categories"));
		req.setAttribute("recommendArticles", map.get("recommendArticles"));
		req.setAttribute("newArticles", map.get("newArticles"));
		return "jsp/index.jsp";
	}
	
	//getters and setters
	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public IndexService getIndexService() {
		return indexService;
	}
	
	@Resource(name="indexService")
	public void setIndexService(IndexService indexService) {
		this.indexService = indexService;
	}

}
