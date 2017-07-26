package blog.model;

import java.io.Serializable;
import java.sql.Date;

/** 
 * 附件的POJO类
 * @author zjz
 */
public class Attach implements Serializable{
	private static final long serialVersionUID = -1306173552156196484L;
	
	private int id;
	private Article article;
	private String path;
	private Date uploadTime;
	
	public int getId() {
		return id;
	}
	public Article getArticle() {
		return article;
	}
	public String getPath() {
		return path;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	@Override
	public int hashCode() {
		return path.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Attach){
			Attach attach = (Attach)obj;
			return attach.path.equals(path);
		}else{
			return false;
		}
	}
	@Override
	public String toString() {
		return path;
	}
}
