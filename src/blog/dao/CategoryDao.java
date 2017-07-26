package blog.dao;

import java.util.List;

import blog.model.Category;

/** 
 * 类别管理DAO接口
 * @author zjz
 */
public interface CategoryDao {
	
	/**
	 * 获取类别的基本信息
	 * @param cid
	 * @return
	 */
	public Category getCategory(int cid);
	
	/**
	 * 获取当前所有类别列表
	 * @return
	 */
	public List<Category> getCategoryList();
	
	/**
	 * 获取该类别下文章数
	 * @param cid
	 * @return
	 */
	public int getArticleNum(int cid);
	
	/**
	 * 删除一个类别
	 * @param cid
	 */
	public void deleteCategory(int cid);
	
	/**
	 * 添加类别
	 * @param category
	 */
	public void addCategory(Category category);
	
	/**
	 * 更新一个类别
	 * @param category
	 */
	public void updateCategory(Category category);
}
