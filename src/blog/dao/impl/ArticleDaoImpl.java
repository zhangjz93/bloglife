package blog.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import blog.dao.ArticleDao;
import blog.dao.mappers.ArticleMapper;
import blog.dao.mappers.ContentMapper;
import blog.model.Article;
import blog.model.Content;

/** 
 * 文章管理DAO实现类
 * @author zjz
 */
@Repository("articleDao")
public class ArticleDaoImpl implements ArticleDao {
	private JdbcTemplate jt;

	/**
	 * @see blog.dao.ArticleDao#getArticleList(int, int, int)
	 */
	public List<Article> getArticleList(int cid, int start, int num) {
		String sql = "select * from article where category_id=? order by submittime desc limit ?,?";
		List<Article> articles = jt.query(sql, new Object[]{cid,start,num}, new ArticleMapper());
		return articles;
	}
	
	/**
	 * @see blog.dao.ArticleDao#getArticleList(int, int, java.lang.String)
	 */
	public List<Article> getArticleList(int start, int num, String searchTitle){
		String sql = "select * from article where title like '" + searchTitle + "%' order by submittime desc limit ?,?";
		List<Article> articles = jt.query(sql, new Object[]{start,num}, new ArticleMapper());
		return articles;
	}
	
	/**
	 * @see blog.dao.ArticleDao#getRecommendList(int, int)
	 */
	public List<Article> getRecommendList(int start, int num){
		String sql = "select a.* from recommend r join article a on r.article_id=a.id order by a.submittime desc limit ?,?";
		List<Article> articles = jt.query(sql, new Object[]{start,num}, new ArticleMapper());
		return articles;
	}
	
	/**
	 * @see blog.dao.ArticleDao#getArticleListOfUser(int, int, int)
	 */
	public List<Article> getArticleListOfUser(int uid, int start, int num){
		String sql = "select * from article where writer_id=? order by submittime desc limit ?,?";
		List<Article> articles = jt.query(sql, new Object[]{uid,start,num}, new ArticleMapper());
		return articles;
	}
	
	/**
	 * @see blog.dao.ArticleDao#getArticle(int)
	 */
	public Article getArticle(int aid){
		String sql = "select * from article where id=?";
		List<Article> articles = jt.query(sql, new Object[]{aid}, new ArticleMapper());
		return articles.size()==0 ? null : articles.get(0);
	}
	
	/**
	 * @see blog.dao.ArticleDao#getArticle(int, int)
	 */
	public Article getArticle(int aid, int wid){
		String sql = "select * from article where id=? and writer_id=?";
		List<Article> articles = jt.query(sql, new Object[]{aid,wid}, new ArticleMapper());
		return articles.size()==0 ? null : articles.get(0);
	}
	
	/**
	 * @see blog.dao.ArticleDao#getContent(int)
	 */
	public Content getContent(int aid){
		String sql = "select * from content where article_id=?";
		List<Content> contents = jt.query(sql, new Object[]{aid}, new ContentMapper());
		return contents.size()==0 ? null : contents.get(0);
	}
	
	/**
	 * @see blog.dao.ArticleDao#getArticleNum(int)
	 */
	public int getArticleNum(int cid){
		String sql = "select count(id) from article where category_id=?";
		return jt.queryForObject(sql, new Object[]{cid}, Integer.class);
	}
	
	/**
	 * @see blog.dao.ArticleDao#getArticleNum()
	 */
	public int getArticleNum(String searchTitle){
		String sql = "select count(id) from article where title like '" + searchTitle + "%'";
		return jt.queryForObject(sql, Integer.class);
	}
	
	/**
	 * @see blog.dao.ArticleDao#getRecommendNum()
	 */
	public int getRecommendNum(){
		String sql = "select count(article_id) from recommend";
		return jt.queryForObject(sql, Integer.class);
	}
	
	/**
	 * @see blog.dao.ArticleDao#getReplyNum(int)
	 */
	public int getReplyNum(int aid){
		String sql = "select count(id) from reply where article_id=?";
		return jt.queryForObject(sql, new Object[]{aid}, Integer.class);
	}
	
	/**
	 * @see blog.dao.ArticleDao#addArticle(blog.model.Article)
	 */
	public int addArticle(final Article article){
		final String sql_1 = "insert into article values(null,?,?,?,?,0,0,?,0,?,0,0)";
		String sql_2 = "insert into content values(null,?,?)";
		//添加基本信息
		KeyHolder kh = new GeneratedKeyHolder();
		jt.update(new PreparedStatementCreator(){
			//override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql_1);
				Timestamp now = new Timestamp(System.currentTimeMillis());
				ps.setString(1, article.getTitle());
				ps.setInt(2, article.getWriter().getId());
				ps.setInt(3, article.getCategory().getId());
				ps.setTimestamp(4, now);
				ps.setTimestamp(5, now);
				ps.setString(6, article.getSummary());
				return ps;
			}
		}, kh);
		int key = kh.getKey().intValue();  //获取key
		//添加内容
		jt.update(sql_2, new Object[]{key,article.getContent().getWords()});
		return key;
	}
	
	/**
	 * @see blog.dao.ArticleDao#updateArticle(blog.model.Article)
	 */
	public void updateArticle(Article article){
		String sql_1 = "update article set title=?,edittime=?,isedit=1,summary=? where id=?";
		String sql_2 = "update content set words=? where article_id=?";
		jt.update(sql_1, new Object[]{article.getTitle(),new Timestamp(System.currentTimeMillis()),
				article.getSummary(),article.getId()});
		jt.update(sql_2, new Object[]{article.getContent().getWords(), article.getId()});
	}
	
	/**
	 * @see blog.dao.ArticleDao#deleteArticle(int, int)
	 */
	public int deleteArticle(int aid, int wid){
		String sql = "delete from article where id=? and writer_id=?";
		return jt.update(sql, new Object[]{aid,wid});
	}
	
	/**
	 * @see blog.dao.ArticleDao#deleteArticle(int)
	 */
	public void deleteArticle(int aid){
		String sql = "delete from article where id=?";
		jt.update(sql, new Object[]{aid});
	}
	
	/**
	 * @see blog.dao.ArticleDao#addClickNum(int)
	 */
	public void addClickNum(int aid, int toplimit){
		String sql = "update article set clicknum=clicknum+1 where id=? and clicknum<?";
		jt.update(sql, new Object[]{aid,toplimit});
	}
	
	/**
	 * @see blog.dao.ArticleDao#getArticleNumOfUser(int)
	 */
	public int getArticleNumOfUser(int uid){
		String sql = "select count(id) from article where writer_id=?";
		return jt.queryForObject(sql, new Object[]{uid}, Integer.class);
	}
	
	/**
	 * @see blog.dao.ArticleDao#updateLastCount(int)
	 */
	public void updateLastCount(int aid){
		String sql = "update article set lastcount=lastcount+1 where id=?";
		jt.update(sql, new Object[]{aid});
	}
	
	/**
	 * @see blog.dao.ArticleDao#getHotArticleList(int, int)
	 */
	public List<Article> getHotArticleList(int cid, int num){
		String sql = "select * from article where category_id=? order by replynum desc limit 0,?";
		List<Article> articles = jt.query(sql, new Object[]{cid,num}, new ArticleMapper());
		return articles;
	}
	
	/**
	 * @see blog.dao.ArticleDao#getNewArticleList(int)
	 */
	public List<Article> getNewArticleList(int num){
		String sql = "select * from article order by submittime desc limit 0,?";
		List<Article> articles = jt.query(sql, new Object[]{num}, new ArticleMapper());
		return articles;
	}
	
	/**
	 * @see blog.dao.ArticleDao#addRecommend(int)
	 */
	public void addRecommend(int aid){
		String sql_1 = "insert into recommend values(?,?)";
		String sql_2 = "update article set isgood=1 where id=?";
		jt.update(sql_1, new Object[]{aid, new Timestamp(System.currentTimeMillis())});
		jt.update(sql_2, new Object[]{aid});
	}
	
	/**
	 * @see blog.dao.ArticleDao#deleteRecommend(int)
	 */
	public void deleteRecommend(int aid){
		String sql_1 = "delete from recommend where article_id=?";
		String sql_2 = "update article set isgood=0 where id=?";
		jt.update(sql_1, new Object[]{aid});
		jt.update(sql_2, new Object[]{aid});
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
