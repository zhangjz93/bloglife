package blog.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import blog.enumeration.ErrorEnum;
import blog.model.Reply;
import blog.model.User;
import blog.service.ReplyService;

/** 
 * 关于回复的Controller
 * @author zjz
 */
@Controller
public class ReplyController extends BaseController{
	private ReplyService replyService;
	
	/**
	 * 转到回复列表页面
	 * @param aid
	 * @param req
	 * @return
	 */
	@RequestMapping("/replies/{aid}")
	public String goToReplyList(@PathVariable("aid")int aid, int page, HttpServletRequest req){
		if(page < 1)
			page = 1;
		Map<String,Object> map = replyService.getReplyList(aid, page, true);
		if(map.get("article") == null){
			return jumpToErrorPage(req, ErrorEnum.EXIST);
		}
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPage", map.get("totalPage"));
		req.setAttribute("replyNum", map.get("replyNum"));
		req.setAttribute("article", map.get("article"));
		req.setAttribute("replies", map.get("replies"));
		return "jsp/replies.jsp";
	}
	
	/**
	 * 添加回复
	 * @param reply
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/addReply", method=RequestMethod.POST)
	public String addReply(Reply reply, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		int articleId = reply.getArticle().getId();
		reply.setWriter(user);
		boolean isSuccess = replyService.addReply(reply);
		if(!isSuccess){
			return jumpToErrorPage(req, ErrorEnum.FORMAT);
		}
		return "redirect: content/" + articleId;  
	}
	
	/**
	 * 删除回复
	 * @param rid
	 * @param res
	 */
	@RequestMapping(value="/deleteReply", method=RequestMethod.POST)
	public void deleteReply(int rid, HttpServletResponse res, HttpServletRequest req){
		User user = (User)req.getSession().getAttribute("user");
		boolean isSuccess = replyService.deleteReply(rid, user.getId());
		writeMessage(isSuccess, res);
	}

	//getters and setters
	public ReplyService getReplyService() {
		return replyService;
	}
	
	@Resource(name="replyService")
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}
}
