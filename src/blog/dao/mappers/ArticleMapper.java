package blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import blog.model.Article;
import blog.model.Category;
import blog.model.User;
import blog.util.DateUtil;

/** 
 * @author zjz
 */
public class ArticleMapper implements RowMapper<Article> {

	public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
		Article article = new Article();
		User writer = new User();
		Category category = new Category();
		article.setId(rs.getInt("id"));
		article.setTitle(rs.getString("title"));
		article.setSubmittime(DateUtil.getConvertedTime(rs.getTimestamp("submittime")));
		article.setEdittime(DateUtil.getConvertedTime(rs.getTimestamp("edittime")));
		article.setClickNum(rs.getInt("clicknum"));
		article.setReplyNum(rs.getInt("replynum"));
		article.setEdit(rs.getBoolean("isedit"));
		article.setSummary(rs.getString("summary"));
		article.setGood(rs.getBoolean("isgood"));
		article.setLastCount(rs.getInt("lastcount"));
		writer.setId(rs.getInt("writer_id"));
		category.setId(rs.getInt("category_id"));
		article.setWriter(writer);
		article.setCategory(category);
		return article;
	}
	
}
