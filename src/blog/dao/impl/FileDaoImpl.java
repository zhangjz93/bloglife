package blog.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import blog.dao.FileDao;
import blog.dao.mappers.AttachMapper;
import blog.model.Attach;

/** 
 * FileDao实现类
 * @author zjz
 */
@Repository("fileDao")
public class FileDaoImpl implements FileDao {
	private JdbcTemplate jt;
	
	/**
	 * @see blog.dao.FileDao#addPaths(int, java.util.List)
	 */
	public void addPaths(final int aid, final List<String> paths){
		if(paths == null)
			return;
		final String sql = "insert into attach values(null,?,?,?)";
		final Date today = new Date(System.currentTimeMillis());
		BatchPreparedStatementSetter setter=new BatchPreparedStatementSetter (){
			//override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				String path = paths.get(i);
				ps.setInt(1, aid);
				ps.setString(2, path);
				ps.setDate(3, today);
			}
			//override
			public int getBatchSize() {
				return paths.size();
			}
	    };
	    jt.batchUpdate(sql, setter); 
	}
	
	/**
	 * @see blog.dao.FileDao#deletePaths(int)
	 */
	public void deletePaths(int aid){
		String sql = "delete from attach where article_id=?";
		jt.update(sql, new Object[]{aid});
	}
	
	/**
	 * @see blog.dao.FileDao#getAttachesOfDate(java.sql.Date)
	 */
	public List<Attach> getAttachesOfDate(Date date){
		String sql = "select * from attach where uploadtime=?";
		List<Attach> attaches = jt.query(sql, new Object[]{date}, new AttachMapper());
		return attaches;
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
