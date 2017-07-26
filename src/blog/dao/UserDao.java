package blog.dao;

import java.util.List;

import blog.model.User;

/**
 * 用户管理DAO接口
 * @author zjz
 */
public interface UserDao {
		
	/**
	 * @param user
	 * @return 插入后对应的id值
	 */
	public int addUser(User user);
	
	/**
	 * 更新个人信息
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 根据昵称查找用户
	 * @param name
	 * @return 若不存在则返回null
	 */
	public User getUser(String name);
	
	/**
	 * 按id获取用户信息
	 * @param uid
	 * @return 若不存在则返回null
	 */
	public User getUserById(int uid);
	
	/**
	 * 获取用户列表，包含用户的角色信息
	 * @param start
	 * @param num
	 * @param searchName 条件搜索值，默认为"all"
	 * @return
	 */
	public List<User> getUserList(int start, int num, String searchName);
	
	/**
	 * 获取用户数量
	 * @param userName 查询的用户名
	 * @return
	 */
	public int getUserNum(String userName);
	
	/**
	 * 删除用户
	 * @param uid
	 */
	public void deleteUser(int uid);
	
	/**
	 * 更新用户头像
	 * @param uid
	 * @param path
	 */
	public void updatePhoto(int uid, String path);
}
