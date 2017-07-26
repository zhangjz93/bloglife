package blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.AttentionDao;
import blog.dao.UserDao;
import blog.model.User;
import blog.service.AttentionService;
import blog.service.UserService;
import blog.util.PageUtil;
import blog.util.PropertiesLoader;

/** 
 * AttentionService实现类
 * @author zjz
 */
@Service("attentionService")
public class AttentionServiceImpl implements AttentionService {
	private AttentionDao attentionDao;
	private UserDao userDao;
	private UserService userService;
	private PropertiesLoader config;

	/**
	 * @see blog.service.AttentionService#addAttention(int, int)
	 */
	@Transactional
	public boolean addAttention(int uid, int focused){
		if(uid == focused)
			return false;  //关注者与被关注者id相同，不允许，退出
		attentionDao.addAttention(uid, focused);
		return true;
	}
	
	/**
	 * 取关
	 * @see blog.service.AttentionService#cancelAttention(int, int)
	 */
	@Transactional
	public boolean cancelAttention(int uid, int focusedId){
		attentionDao.cancelAttention(uid, focusedId);
		return true;
	}
	
	/**
	 * @see blog.service.AttentionService#getFocusList(int, int, int, boolean)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getFocusList(int uid, int visitorId, int page, boolean isFocus){
		Map<String,Object> userMap = userService.getUserInfo(uid, visitorId);
		int pageSize = config.getInt("attention.pageSize");
		if(userMap == null)
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		int start = (page-1) * pageSize;
		List<User> users = attentionDao.getAttentionList(uid, isFocus, start, pageSize);
		//查询是否被关注
		for(int i=0; i<users.size(); i++){
			boolean isFocused = false;
			User user = users.get(i);
			if(visitorId != 0){
				isFocused = attentionDao.isFocusedByUser(user.getId(), visitorId);
			}
			user.setFocused(isFocused);
		}
		int attentionNum = attentionDao.getAttentionNum(uid, isFocus);
		int totalPage = PageUtil.getTotalPage(attentionNum, pageSize);
		map.put("userMap", userMap);
		map.put("users", users);
		map.put("attentionNum", attentionNum);
		map.put("totalPage", totalPage);
		return map;
	}
		
	//getters and getters
	public AttentionDao getAttentionDao() {
		return attentionDao;
	}
	
	@Resource(name="attentionDao")
	public void setAttentionDao(AttentionDao attentionDao) {
		this.attentionDao = attentionDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public PropertiesLoader getConfig() {
		return config;
	}
	
	@Resource(name="config")
	public void setConfig(PropertiesLoader config) {
		this.config = config;
	}

}
