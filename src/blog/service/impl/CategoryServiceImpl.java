package blog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.CategoryDao;
import blog.enumeration.ValueConstraint;
import blog.model.Category;
import blog.service.CategoryService;
import blog.util.ValidationUtil;

/** 
 * CategoryService实现类
 * @author zjz
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	private CategoryDao categoryDao;
	
	/**
	 * @see blog.service.CategoryService#getCategory(int)
	 */
	@Transactional(readOnly=true)
	public Category getCategory(int cid){
		return categoryDao.getCategory(cid);
	}
	
	/**
	 * @see blog.service.CategoryService#getCategoryList()
	 */
	@Transactional(readOnly=true)
	public List<Category> getCategoryList(){
		return categoryDao.getCategoryList();
	}
	
	/**
	 * @see blog.service.CategoryService#deleteCategory(int)
	 */
	@Transactional
	public boolean deleteCategory(int cid){
		categoryDao.deleteCategory(cid);
		return true;
	}
	
	/**
	 * @see blog.service.CategoryService#addCategory(blog.model.Category)
	 */
	@Transactional
	public boolean addCategory(Category category){
		if(categoryValidation(category)){
			categoryDao.addCategory(category);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @see blog.service.CategoryService#updateCategory(blog.model.Category)
	 */
	@Transactional
	public boolean updateCategory(Category category){
		if(categoryValidation(category)){
			categoryDao.updateCategory(category);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 验证类别更新表单
	 * @param category
	 * @return
	 */
	private boolean categoryValidation(Category category){
		return ValidationUtil.isText(category.getName(), ValueConstraint.MIN_CATEGORY_NAME, ValueConstraint.MAX_CATEGORY_NAME) &&
				ValidationUtil.isText(category.getIntro(), ValueConstraint.MIN_CATEGORY_INTRO, ValueConstraint.MAX_CATEGORY_INTRO) &&
				ValidationUtil.isNum(category.getSortNum(), 0, 100) && 
				ValidationUtil.isNotNull(category.getPhoto());
	}
	
	//getters and setters
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	
	@Resource(name="categoryDao")
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
}
