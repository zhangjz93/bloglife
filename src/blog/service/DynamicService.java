package blog.service;

import java.util.Map;

/** 
 * 动态管理Service接口
 * @author zjz
 */
public interface DynamicService {
	
	/**
	 * 获取动态列表
	 * @param uid
	 * @param page
	 * @return
	 */
	public Map<String,Object> getDynamicList(int uid, int page);
	
}
