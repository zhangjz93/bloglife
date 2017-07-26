package blog.model;

import java.io.Serializable;

/** 
 * 回复的POJO类
 * @author zjz
 */
public class Reply implements Serializable{
	private static final long serialVersionUID = -2443604791401445422L;
	
	private int id;
	private User writer;
	private Article article;
	private Reply parent;
	private String words;
	private String replytime;
	private int count;  //楼层
	
	public int getId() {
		return id;
	}
	public User getWriter() {
		return writer;
	}
	public Article getArticle() {
		return article;
	}
	public Reply getParent() {
		return parent;
	}
	public String getWords() {
		return words;
	}
	public String getReplytime() {
		return replytime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setWriter(User writer) {
		this.writer = writer;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public void setParent(Reply parent) {
		this.parent = parent;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public void setReplytime(String replytime) {
		this.replytime = replytime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
