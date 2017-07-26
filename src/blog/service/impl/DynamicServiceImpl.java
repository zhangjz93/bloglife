package blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.ArticleDao;
import blog.dao.CategoryDao;
import blog.dao.DynamicDao;
import blog.dao.UserDao;
import blog.model.Article;
import blog.model.Dynamic;
import blog.service.DynamicService;
import blog.service.UserService;
import blog.util.PageUtil;
import blog.util.PropertiesLoader;

/** 
 * DynamicService实现类
 * @author zjz
 */
@Service("dynamicService")
public class DynamicServiceImpl implements DynamicService {
	private DynamicDao dynamicDao;
	private UserDao userDao;
	private ArticleDao articleDao;
	private CategoryDao categoryDao;
	private UserService userService;
	private PropertiesLoader config;
	
	/**
	 * @see blog.service.DynamicService#getDynamicList(int, int)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getDynamicList(int uid, int page){
		Map<String,Object> userMap = userService.getUserInfo(uid, 0);
		int pageSize = config.getInt("dynamic.pageSize");
		if(userMap == null)
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		int start = (page-1) * pageSize;
		List<Dynamic> dynamics = dynamicDao.getDynamicList(uid, start, pageSize);
		for(int i=0; i<dynamics.size(); i++){
			Dynamic dynamic = dynamics.get(i);
			dynamic.setUser(userDao.getUserById(dynamic.getUser().getId()));
			Article article = articleDao.getArticle(dynamic.getArticle().getId());  //文章信息
			if(article != null){
				article.setCategory(categoryDao.getCategory(article.getCategory().getId()));  //类别信息
			}
			dynamic.setArticle(article);  //文章动态，不存在为null
		}
		int dynamicNum = dynamicDao.getDynamicNum(uid);
		int totalPage = PageUtil.getTotalPage(dynamicNum, pageSize);
		map.put("userMap", userMap);
		map.put("dynamics", dynamics);
		map.put("dynamicNum", dynamicNum);
		map.put("totalPage", totalPage);
		return map;
	}

	//getters and setters
	public DynamicDao getDynamicDao() {
		return dynamicDao;
	}
	
	@Resource(name="dynamicDao")
	public void setDynamicDao(DynamicDao dynamicDao) {
		this.dynamicDao = dynamicDao;
	}

	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}
	
	@Resource(name="articleDao")
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
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
