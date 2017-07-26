package blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import blog.model.Article;
import blog.model.Dynamic;
import blog.model.User;
import blog.util.DateUtil;

/** 
 * @author zjz
 */
public class DynamicMapper implements RowMapper<Dynamic> {

	public Dynamic mapRow(ResultSet rs, int rowNum) throws SQLException {
		Dynamic dynamic = new Dynamic();
		User user = new User();
		Article article = new Article();
		dynamic.setId(rs.getInt("id"));
		dynamic.setType(rs.getInt("type"));
		dynamic.setSubmitTime(DateUtil.getConvertedTime(rs.getTimestamp("submittime")));
		user.setId(rs.getInt("user_id"));
		article.setId(rs.getInt("message_id"));
		dynamic.setUser(user);
		dynamic.setArticle(article);
		return dynamic;
	}

}
