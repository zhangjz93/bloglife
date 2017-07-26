package blog.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import blog.model.Permission;

/** 
 * @author zjz
 */
public class PermissionMapper implements RowMapper<Permission> {

	public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
		Permission permission = new Permission();
		permission.setId(rs.getInt("id"));
		permission.setUrl(rs.getString("url"));
		return permission;
	}

}
