package blog.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import blog.dao.ArticleDao;
import blog.dao.DynamicDao;
import blog.dao.mappers.DynamicMapper;
import blog.model.Dynamic;

/** 
 * DynamicDao实现类
 * @author zjz
 */
@Repository("dynamicDao")
public class DynamicDaoImpl implements DynamicDao {
	private JdbcTemplate jt;
	
	private ArticleDao articleDao;
	
	/**
	 * @see blog.dao.DynamicDao#addDynamic(blog.model.Dynamic)
	 */
	public void addDynamic(Dynamic dynamic){
		String sql = "insert into dynamic values(null,?,?,?,?)";
		jt.update(sql, new Object[]{dynamic.getUser().getId(),dynamic.getArticle().getId(),dynamic.getType(),new Timestamp(System.currentTimeMillis())});
	}
	
	/**
	 * @see blog.dao.DynamicDao#getDynamicList(int, int, int)
	 */
	public List<Dynamic> getDynamicList(int uid, int start, int num){
		String sql = "select * from dynamic where user_id in (select focused_id from attention where focus_id=?) order by submittime desc limit ?,?";
		List<Dynamic> dynamics = jt.query(sql, new Object[]{uid,start,num}, new DynamicMapper());
		return dynamics;
	}
	
	/**
	 * @see blog.dao.DynamicDao#getDynamicNum(int)
	 */
	public int getDynamicNum(int uid){
		String sql = "select count(*) from dynamic where user_id in (select focused_id from attention where focus_id=?)";
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

	public ArticleDao getArticleDao() {
		return articleDao;
	}
	
	@Resource(name="articleDao")
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
}
