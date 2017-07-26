package blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import blog.model.Article;
import blog.model.Attach;

/** 
 * @author zjz
 */
public class AttachMapper implements RowMapper<Attach>{

	public Attach mapRow(ResultSet rs, int rowNum) throws SQLException {
		Attach attach = new Attach();
		Article article = new Article();
		attach.setId(rs.getInt("id"));
		//只提取文件名部分
		String path = rs.getString("path");
		attach.setPath(path.substring(path.lastIndexOf("/")+1));
		attach.setUploadTime(rs.getDate("uploadtime"));
		article.setId(rs.getInt("article_id"));
		attach.setArticle(article);
		return attach;
	}
	
}
