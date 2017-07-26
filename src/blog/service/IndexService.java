package blog.service;

import java.util.Map;

/** 
 * 关于跳至主页相关的Service接口，用于一些基本信息的载入
 * @author zjz
 */
public interface IndexService {
	
	/**
	 * 获取主页信息
	 * @return
	 */
	public Map<String,Object> getIndexInfo();
}
