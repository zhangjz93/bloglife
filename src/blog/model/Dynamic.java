package blog.model;

import java.io.Serializable;

/** 
 * 用户动态的POJO类
 * @author zjz
 */
public class Dynamic implements Serializable{
	private static final long serialVersionUID = -4014784819918224334L;
	
	private int id;
	private User user;
	private Article article;  
	private int type;  //动态类型，1为文章
	private String submitTime;
	
	public int getId() {
		return id;
	}
	public User getUser() {
		return user;
	}
	public int getType() {
		return type;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	
}
