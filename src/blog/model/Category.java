package blog.model;

import java.io.Serializable;

/** 
 * 文章类别的POJO类
 * @author zjz
 */
public class Category implements Serializable{
	private static final long serialVersionUID = 7290599349424697567L;
	
	private int id;
	private String name;
	private String intro;  //类别介绍
	private String photo;  //图标路径
	private int articleNum;  //该类别下的文章数量
	private int sortNum;  //排序
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIntro() {
		return intro;
	}
	public String getPhoto() {
		return photo;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getArticleNum() {
		return articleNum;
	}
	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}
	public int getSortNum() {
		return sortNum;
	}
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
}
