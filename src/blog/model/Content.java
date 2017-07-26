package blog.model;

import java.io.Serializable;

/** 
 * 文章正文的POJO类
 * @author zjz
 */
public class Content implements Serializable{
	private static final long serialVersionUID = 2532355936291208882L;
	
	private int id;
	private Article article;
	private String words;
	
	public int getId() {
		return id;
	}
	public Article getArticle() {
		return article;
	}
	public String getWords() {
		return words;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public void setWords(String words) {
		this.words = words;
	}
}
