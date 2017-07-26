package blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import blog.model.Article;
import blog.model.Reply;
import blog.model.User;
import blog.util.DateUtil;

/** 
 * @author zjz
 */
public class ReplyMapper implements RowMapper<Reply> {

	public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
		Reply reply = new Reply();
		User writer = new User();
		Reply parent = new Reply();
		Article article = new Article();
		reply.setId(rs.getInt("id"));
		reply.setWords(rs.getString("words"));
		reply.setReplytime(DateUtil.getConvertedTime(rs.getTimestamp("replytime")));
		reply.setCount(rs.getInt("count"));
		writer.setId(rs.getInt("writer_id"));
		parent.setId(rs.getInt("parent_id"));
		article.setId(rs.getInt("article_id"));
		reply.setArticle(article);
		reply.setWriter(writer);
		reply.setParent(parent);
		return reply;
	}
	
}
