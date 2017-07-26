package blog.service;

import java.util.List;

import blog.model.Category;

/** 
 * 类别管理Service层
 * @author zjz
 */
public interface CategoryService {
	
	/**
	 * 获取类别基本信息
	 * @param cid
	 * @return 不存在则返回null
	 */
	public Category getCategory(int cid);
	
	/**
	 * 获取当前所有类别列表，并获取当前类别下文章数量
	 * @return
	 */
	public List<Category> getCategoryList();
	
	/**
	 * 删除一个类别
	 * @param cid
	 * @return
	 */
	public boolean deleteCategory(int cid);
	
	/**
	 * 添加一个类别
	 * @param category
	 * @return
	 */
	public boolean addCategory(Category category);
	
	/**
	 * 更新一个类别
	 * @param category
	 * @return
	 */
	public boolean updateCategory(Category category);
}
