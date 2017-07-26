package blog.cache;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.ehcache.Cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import blog.model.User;
import blog.util.CacheUtil;

/** 
 * 用户相关AOP类
 * @author zjz
 */
@Aspect
@Component
public class UserCacheInterceptor {
	private CacheUtil cacheUtil;
	private Cache userCache;
	
	@PostConstruct
	public void init(){
		userCache = cacheUtil.getCache("userCache");
	}
	
	/**
	 * 获取用户信息
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(public * blog.dao.impl.UserDaoImpl.getUserById(..))")
	public Object aroundGetUserById(ProceedingJoinPoint jp) throws Throwable{
		int userId = (Integer)jp.getArgs()[0];
		return cacheUtil.executeAroundMethod(jp, userCache, userId);
	}
	
	@After("execution(public * blog.service.impl.UserServiceImpl.deleteUser(..))")
	public void afterDeleteUser(JoinPoint jp){
		int userId = (Integer)jp.getArgs()[0];
		userCache.remove(userId);
	}
	
	@After("execution(public * blog.service.impl.UserServiceImpl.updateUser(..))")
	public void afterUpdateUser(JoinPoint jp){
		User user = (User)jp.getArgs()[0];
		userCache.remove(user.getId());
	}
	
	@After("execution(public * blog.service.impl.FileServiceImpl.uploadPhoto(..))")
	public void afterUploadPhoto(JoinPoint jp){
		int userId = (Integer)jp.getArgs()[3];
		userCache.remove(userId);
	}
	
	//getters and setters
	public CacheUtil getCacheUtil() {
		return cacheUtil;
	}
	
	@Resource(name="cacheUtil")
	public void setCacheUtil(CacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
	
	
}
