package blog.dao;

import java.util.List;

import blog.model.Dynamic;

/** 
 * 动态管理DAO接口
 * @author zjz
 */
public interface DynamicDao {
	
	/**
	 * 添加动态
	 * @param dynamic
	 */
	public void addDynamic(Dynamic dynamic);
	
	/**
	 * 获取用户关注者所有动态
	 * @param uid
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Dynamic> getDynamicList(int uid, int start, int num);
	
	/**
	 * 获取动态数量
	 * @param uid
	 * @return
	 */
	public int getDynamicNum(int uid);
}
