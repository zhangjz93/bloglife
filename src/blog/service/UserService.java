package blog.service;

import java.util.Map;

import blog.model.User;

/**
 * 用户管理接口定义
 * @author zjz
 */
public interface UserService {
	
	/**
	 * 用户第三方登录，通过授权返回的用户名查找，若没有则添加该用户
	 * @param user
	 * @return
	 */
	public User login(User user);
	
	/**
	 * 获取用户信息，用于个人名片展示
	 * @param uid
	 * @param visitorId 访问者id，未登录则为0
	 * @return
	 */
	public Map<String,Object> getUserInfo(int uid, int visitorId);
	
	/**
	 * 更新个人信息
	 * @param user
	 * @return 返回更新后个人信息的JSON
	 */
	public String updateUser(User user);
	
	/**
	 * 获取用户列表
	 * @param page
	 * @param searchName
	 * @return
	 */
	public Map<String,Object> getUserList(int page, String searchName);
	
	/**
	 * 删除用户
	 * @param uid
	 * @return
	 */
	public boolean deleteUser(int uid);
}
