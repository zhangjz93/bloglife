package blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import blog.model.User;

/** 
 * @author zjz
 */
public class UserMapper implements RowMapper<User> {
	
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setPhoto(rs.getString("photo"));
		user.setMaster(rs.getBoolean("is_master"));
		user.setRegistTime(rs.getDate("registtime"));
		user.setIntro(rs.getString("intro"));
		user.setCareer(rs.getString("career"));
		user.setHobby(rs.getString("hobby"));
		user.setSkill(rs.getString("skill"));
		return user;
	}
	
}
