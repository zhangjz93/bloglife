package blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.ArticleDao;
import blog.dao.NoticeDao;
import blog.dao.ReplyDao;
import blog.dao.UserDao;
import blog.model.Article;
import blog.model.Reply;
import blog.model.User;
import blog.service.NoticeService;
import blog.service.UserService;
import blog.util.PageUtil;
import blog.util.PropertiesLoader;

/** 
 * NoticeService实现类
 * @author zjz
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	private NoticeDao noticeDao;
	private UserDao userDao;
	private ReplyDao replyDao;
	private ArticleDao articleDao;
	private UserService userService;
	private PropertiesLoader config;

	/**
	 * @see blog.service.NoticeService#getNoticeList(int, int, boolean)
	 */
	@Transactional
	public Map<String,Object> getNoticeList(int uid, int page){
		Map<String,Object> userMap = userService.getUserInfo(uid, 0);
		int pageSize = config.getInt("notice.pageSize");
		if(userMap == null)
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		int start = (page-1) * pageSize;
		List<Reply> replies = noticeDao.getNoticeList(uid, start, pageSize);
		for(int i=0; i<replies.size(); i++){
			Reply reply = replies.get(i);
			User writer = userDao.getUserById(reply.getWriter().getId());  //作者信息
			reply.setWriter(writer);
			Article article = articleDao.getArticle(reply.getArticle().getId());  //文章信息
			reply.setArticle(article);
			//父回复
			int parentId = reply.getParent().getId();
			if(parentId != 0){
				Reply parent = replyDao.getReply(parentId);   //不存在则为null
				if(parent != null){  
					parent.setWriter(userDao.getUserById(parent.getWriter().getId()));
					reply.setParent(parent);
				}else{
					parent = new Reply();
					parent.setId(-1);
					reply.setParent(parent);
				}
			}
		}
		int noticeNum = noticeDao.getNoticeNumOfUser(uid);
		int totalPage = PageUtil.getTotalPage(noticeNum, pageSize);
		if(page == 1){
			noticeDao.updateUnreadNotice(uid);  //更新为已读
		}
		map.put("userMap", userMap);
		map.put("replies", replies);
		map.put("noticeNum", noticeNum);
		map.put("totalPage", totalPage);
		return map;
	}
	
	/**
	 * @see blog.service.NoticeService#getUnreadNoticeNum(int)
	 */
	@Transactional(readOnly=true)
	public int getUnreadNoticeNum(int uid){
		return noticeDao.getUnreadNoticeNum(uid);
	}
	
	//getters and setters
	public NoticeDao getNoticeDao() {
		return noticeDao;
	}
	
	@Resource(name="noticeDao")
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}
	
	@Resource(name="articleDao")
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public ReplyDao getReplyDao() {
		return replyDao;
	}
	
	@Resource(name="replyDao")
	public void setReplyDao(ReplyDao replyDao) {
		this.replyDao = replyDao;
	}

	public PropertiesLoader getConfig() {
		return config;
	}
	
	@Resource(name="config")
	public void setConfig(PropertiesLoader config) {
		this.config = config;
	}
}
