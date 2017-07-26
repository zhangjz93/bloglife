package blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.CategoryDao;
import blog.dao.CollectDao;
import blog.model.Article;
import blog.service.CollectService;
import blog.service.UserService;
import blog.util.PageUtil;
import blog.util.PropertiesLoader;

/** 
 * CollectService实现类
 * @author zjz
 */
@Service("collectService")
public class CollectServiceImpl implements CollectService {	
	private CategoryDao categoryDao;
	private CollectDao collectDao;
	private UserService userService;
	private PropertiesLoader config;
	
	/**
	 * @see blog.service.CollectService#collectArticle(int, int)
	 */
	@Transactional
	public boolean collectArticle(int uid, int aid){
		collectDao.addCollect(uid, aid);
		return true;
	}
	
	/**
	 * @see blog.service.CollectService#cancelCollect(int, int)
	 */
	@Transactional
	public boolean cancelCollect(int uid, int aid){
		return collectDao.deleteCollect(uid, aid) != 0;
	}
	
	/**
	 * @see blog.service.CollectService#getCollectOfUser(int, int， int)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getCollectOfUser(int uid, int visitorId, int page){
		Map<String,Object> userMap = userService.getUserInfo(uid, visitorId);
		int pageSize = config.getInt("collect.pageSize");
		if(userMap == null)
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		int start = (page-1) * pageSize;
		List<Article> articles = collectDao.getCollectListOfUser(uid, start, pageSize);  //收藏列表
		for(int i=0; i<articles.size(); i++){
			Article article = articles.get(i);
			article.setCategory(categoryDao.getCategory(article.getCategory().getId()));
		}
		int collectNum = collectDao.getCollectNumOfUser(uid);
		int totalPage = PageUtil.getTotalPage(collectNum, pageSize);
		map.put("userMap", userMap);
		map.put("articles", articles);
		map.put("collectNum", collectNum);
		map.put("totalPage", totalPage);
		return map;
	}
	
	//getters and setters
	public CollectDao getCollectDao() {
		return collectDao;
	}
	
	@Resource(name="collectDao")
	public void setCollectDao(CollectDao collectDao) {
		this.collectDao = collectDao;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	
	@Resource(name="categoryDao")
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public PropertiesLoader getConfig() {
		return config;
	}
	
	@Resource(name="config")
	public void setConfig(PropertiesLoader config) {
		this.config = config;
	}
}
