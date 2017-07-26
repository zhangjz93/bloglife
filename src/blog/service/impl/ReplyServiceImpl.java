package blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.ArticleDao;
import blog.dao.CategoryDao;
import blog.dao.ReplyDao;
import blog.dao.UserDao;
import blog.enumeration.ValueConstraint;
import blog.model.Article;
import blog.model.Reply;
import blog.service.ReplyService;
import blog.util.PageUtil;
import blog.util.PropertiesLoader;
import blog.util.TextUtil;
import blog.util.ValidationUtil;

/** 
 * ReplyService实现类
 * @author zjz
 */
@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
	private ReplyDao replyDao;
	private ArticleDao articleDao;
	private UserDao userDao;
	private CategoryDao categoryDao;
	private PropertiesLoader config;
	
	/**
	 * @see blog.service.ReplyService#getReplyList(int, int, boolean)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getReplyList(int aid, int page, boolean getArticleInfo){
		Map<String,Object> map = new HashMap<String,Object>();
		int pageSize = config.getInt("reply.pageSize");
		int start = (page-1) * pageSize;
		List<Reply> replies = replyDao.getReplies(aid, start, pageSize, false);
		//获取父回复及作者信息
		for(int i=0; i<replies.size(); i++){
			Reply reply = replies.get(i);
			reply.setWriter(userDao.getUserById(reply.getWriter().getId()));  //作者
			int parentId = reply.getParent().getId();
			if(parentId != 0){
				Reply parent = replyDao.getReply(parentId);
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
		int replyNum = replyDao.getReplyNum(aid);  //回复数
		int totalPage = PageUtil.getTotalPage(replyNum, pageSize);  
		map.put("replies", replies);
		map.put("totalPage", totalPage);
		map.put("replyNum", replyNum);
		if(getArticleInfo){
			Article articleInfo = articleDao.getArticle(aid);  //文章信息
			articleInfo.setCategory(categoryDao.getCategory(articleInfo.getCategory().getId())); //类别信息
			map.put("article", articleInfo);
		}
		return map;
	}
	
	/**
	 * @see blog.service.ReplyService#addReply(blog.model.Reply)
	 */
	@Transactional
	public boolean addReply(Reply reply){
		if(replyValidation(reply)){
			reply.setWords(TextUtil.HtmlFormatConverter(reply.getWords()));  //转换为HTML输出格式
			int articleId = reply.getArticle().getId();
			int writerId = reply.getWriter().getId();
			int articleWriterId = reply.getArticle().getWriter().getId();
			int parentWriterId = reply.getParent().getWriter().getId();
			int lastCount = articleDao.getArticle(articleId).getLastCount();  //最后楼层数 
			reply.setCount(lastCount+1);
			int key = replyDao.addReply(reply);
			articleDao.updateLastCount(articleId);  //更新最后楼层
			//添加回复提醒，有bug
			if(writerId != articleWriterId){
				replyDao.addNotice(articleWriterId, key);
			}
			if(parentWriterId!=0 && writerId!=parentWriterId){
				replyDao.addNotice(parentWriterId, key);
			}
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @see blog.service.ReplyService#deleteReply(int, int)
	 */
	@Transactional
	public boolean deleteReply(int rid, int wid){
		replyDao.deleteReply(rid, wid);
		return true;
	}
	
	/**
	 * @see blog.service.ReplyService#deleteReply(int)
	 */
	@Transactional
	public boolean deleteReply(int rid){
		replyDao.deleteReply(rid);
		return true;
	}
	
	/**
	 * 验证回复表单
	 * @param reply
	 * @return
	 */
	private boolean replyValidation(Reply reply){
		return ValidationUtil.isText(reply.getWords(), ValueConstraint.MIN_REPLY, ValueConstraint.MAX_REPLY);
	}
	
	//getters and setters
	public ReplyDao getReplyDao() {
		return replyDao;
	}
	
	@Resource(name="replyDao")
	public void setReplyDao(ReplyDao replyDao) {
		this.replyDao = replyDao;
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}
	
	@Resource(name="articleDao")
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PropertiesLoader getConfig() {
		return config;
	}
	
	@Resource(name="config")
	public void setConfig(PropertiesLoader config) {
		this.config = config;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	
	@Resource(name="categoryDao")
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
}
