package blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import blog.model.Article;
import blog.model.Content;

/** 
 * @author zjz
 */
public class ContentMapper implements RowMapper<Content> {

	public Content mapRow(ResultSet rs, int rowNum) throws SQLException {
		Content content = new Content();
		Article article = new Article();
		content.setId(rs.getInt("id"));
		content.setWords(rs.getString("words"));
		article.setId(rs.getInt("article_id"));
		content.setArticle(article);
		return content;
	}

}
