package blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.ArticleDao;
import blog.dao.CategoryDao;
import blog.model.Article;
import blog.model.Category;
import blog.service.ArticleService;
import blog.service.CategoryService;
import blog.service.IndexService;

/** 
 * IndexService的实现类
 * @author zjz
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {
	private CategoryService categoryService;
	private CategoryDao categoryDao;
	private ArticleDao articleDao;
	private ArticleService articleService;

	/**
	 * @see blog.service.IndexService#getIndexInfo()
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getIndexInfo(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Category> categories = categoryService.getCategoryList();  //类别列表
		List<Article> recommendArticles = articleService.getRecommendList();  //推荐列表
		List<Article> newArticles = articleService.getNewArticleList();  //最新列表
		map.put("categories", categories);
		map.put("recommendArticles", recommendArticles);
		map.put("newArticles", newArticles);
		return map;
	}
	
	//getters and setters
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	
	@Resource(name="categoryDao")
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}
	
	@Resource(name="articleDao")
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}
	
	@Resource(name="categoryService")
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}
	
	@Resource(name="articleService")
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
}
