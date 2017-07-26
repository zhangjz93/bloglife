package blog.model;

import java.io.Serializable;
import java.util.List;

/** 
 * 文章的POJO类
 * @author zjz
 */
public class Article implements Serializable{
	private static final long serialVersionUID = -6263447798489163744L;
	
	private int id;
	private String title;
	private String summary;
	private User writer;
	private Category category;
	private Content content;  //文章内容
	private String submittime;
	private String edittime;  //最后编辑时间
	private boolean isEdit;  //是否编辑
	private boolean isGood;  //是否被推荐
	private int clickNum;
	private int replyNum;
	private int lastCount;
	private List<Reply> replies;
	
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public User getWriter() {
		return writer;
	}
	public Category getCategory() {
		return category;
	}
	public int getClickNum() {
		return clickNum;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setWriter(User writer) {
		this.writer = writer;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public String getSubmittime() {
		return submittime;
	}
	public void setSubmittime(String submittime) {
		this.submittime = submittime;
	}
	public List<Reply> getReplies() {
		return replies;
	}
	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}
	public String getEdittime() {
		return edittime;
	}
	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}
	public boolean getIsEdit() {
		return isEdit;
	}
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public boolean getIsGood() {
		return isGood;
	}
	public void setGood(boolean isGood) {
		this.isGood = isGood;
	}
	public int getLastCount() {
		return lastCount;
	}
	public void setLastCount(int lastCount) {
		this.lastCount = lastCount;
	}
}
