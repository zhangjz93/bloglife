package blog.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class User implements Serializable{
	private static final long serialVersionUID = 8715114438086436805L;
	
	private int id;
	private String name;  //昵称
	private String intro;  //自我介绍
	private String photo;  //头像路径
	private Date registTime;  //注册日期
	private String career;  //职业
	private String hobby;  //兴趣
	private String skill;  //技能
	private List<Role> roles;  //用户拥有的角色列表
	private boolean isMaster;  
	private boolean isFocused;  //标记位，提示是否被某用户关注
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIntro() {
		return intro;
	}
	public Date getRegistTime() {
		return registTime;
	}
	public boolean isMaster() {
		return isMaster;
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
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public boolean getIsFocused() {
		return isFocused;
	}
	public void setFocused(boolean isFocused) {
		this.isFocused = isFocused;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getCareer() {
		return career;
	}
	public String getHobby() {
		return hobby;
	}
	public String getSkill() {
		return skill;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
}
