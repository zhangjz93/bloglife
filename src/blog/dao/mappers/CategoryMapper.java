package blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import blog.model.Category;

/** 
 * @author zjz
 */
public class CategoryMapper implements RowMapper<Category> {

	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		category.setIntro(rs.getString("intro"));
		category.setPhoto(rs.getString("photo"));
		category.setArticleNum(rs.getInt("articlenum"));
		category.setSortNum(rs.getInt("sortnum"));
		return category;
	}

}
