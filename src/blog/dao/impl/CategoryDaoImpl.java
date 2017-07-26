package blog.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import blog.dao.CategoryDao;
import blog.dao.mappers.CategoryMapper;
import blog.model.Category;

/** 
 * CategoryDao实现类
 * @author zjz
 */
@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao {
	private JdbcTemplate jt;

	/**
	 * @see blog.dao.CategoryDao#getCategory(int)
	 */
	public Category getCategory(int cid){
		String sql = "select * from category where id=?";
		List<Category> categories = jt.query(sql, new Object[]{cid}, new CategoryMapper());
		return categories.size()==0 ? null : categories.get(0);
	}
	
	/**
	 * @see blog.dao.CategoryDao#getCategoryList()
	 */
	public List<Category> getCategoryList(){
		String sql = "select * from category order by sortnum";
		List<Category> categories = jt.query(sql, new CategoryMapper());
		return categories;
	}
	
	/**
	 * @see blog.dao.CategoryDao#getArticleNum(int)
	 */
	public int getArticleNum(int cid){
		String sql = "select count(id) from article where category_id=?";
		return jt.queryForObject(sql, new Object[]{cid}, Integer.class);
	}
	
	/**
	 * @see blog.dao.CategoryDao#deleteCategory(int)
	 */
	public void deleteCategory(int cid){
		String sql = "delete from category where id=?";
		jt.update(sql, new Object[]{cid});
	}
	
	/**
	 * @see blog.dao.CategoryDao#addCategory(blog.model.Category)
	 */
	public void addCategory(Category category){
		String sql = "insert into category values(null,?,?,?,0,?)";
		jt.update(sql, new Object[]{category.getName(),category.getIntro(),category.getPhoto(),category.getSortNum()});
	}
	
	/**
	 * @see blog.dao.CategoryDao#updateCategory(blog.model.Category)
	 */
	public void updateCategory(Category category){
		String sql = "update category set name=?,intro=?,sortnum=?,photo=? where id=?";
		jt.update(sql, new Object[]{category.getName(),category.getIntro(),category.getSortNum(),category.getPhoto(),category.getId()});
	}
	
	//getters and setters
	public JdbcTemplate getJt() {
		return jt;
	}
	
	@Resource(name="jdbcTemplate")
	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

}
