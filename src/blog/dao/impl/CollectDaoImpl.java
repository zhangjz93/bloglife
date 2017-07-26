package blog.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import blog.dao.CollectDao;
import blog.dao.mappers.ArticleMapper;
import blog.model.Article;

/** 
 * CollectDao实现类
 * @author zjz
 */
@Repository("collectDao")
public class CollectDaoImpl implements CollectDao {
	private JdbcTemplate jt;
	
	/**
	 * @see blog.dao.CollectDao#addCollect(int, int)
	 */
	public void addCollect(int uid, int aid){
		String sql = "insert ignore into collect values(?,?,?)";  //重复则忽略
		jt.update(sql, new Object[]{uid,aid,new Timestamp(System.currentTimeMillis())});
	}
	
	/**
	 * @see blog.dao.CollectDao#deleteCollect(int, int)
	 */
	public int deleteCollect(int uid, int aid){
		String sql = "delete from collect where article_id=? and user_id=?";
		return jt.update(sql, new Object[]{aid,uid});
	}
	
	/**
	 * @see blog.dao.CollectDao#isUserCollect(int, int)
	 */
	public boolean isUserCollect(int uid, int aid){
		String sql = "select count(user_id) from collect where user_id=? and article_id=?";
		int count = jt.queryForObject(sql, new Object[]{uid,aid}, Integer.class);
		return count==0 ? false : true;
	}
	
	/**
	 * @see blog.dao.CollectDao#getCollectListOfUser(int, int, int)
	 */
	public List<Article> getCollectListOfUser(int uid, int start, int num){
		String sql = "select a.* from collect co join article a on co.article_id=a.id where co.user_id=? order by co.collecttime desc limit ?,?";
		List<Article> articles = jt.query(sql, new Object[]{uid,start,num}, new ArticleMapper());
		return articles;
	}
	
	/**
	 * @see blog.dao.CollectDao#getCollectNumOfUser(int)
	 */
	public int getCollectNumOfUser(int uid){
		String sql = "select count(user_id) from collect where user_id=?";
		return jt.queryForObject(sql, new Object[]{uid}, Integer.class);
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
