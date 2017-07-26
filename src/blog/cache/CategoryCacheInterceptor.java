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

import blog.util.CacheUtil;

/** 
 * 类别相关AOP类
 * @author zjz
 */
@Aspect
@Component
public class CategoryCacheInterceptor {
	private CacheUtil cacheUtil;
	private Cache categoryCache;
	
	@PostConstruct
	public void init(){
		categoryCache = cacheUtil.getCache("categoryCache");
	}
	
	/**
	 * 获取类别信息
	 * @param jp
	 * @return
	 * @throws Throwable 
	 */
	@Around("execution(public * blog.dao.impl.CategoryDaoImpl.getCategory(..))")
	public Object aroundGetCategory(ProceedingJoinPoint jp) throws Throwable{
		int categoryId = (Integer)jp.getArgs()[0];
		return cacheUtil.executeAroundMethod(jp, categoryCache, categoryId);
	}
	
	/**
	 * 更新类别后清空缓存
	 * @param jp
	 */
	@After("execution(public * blog.service.impl.CategoryServiceImpl.deleteCategory(..))")
	public void afterDeleteCategory(JoinPoint jp){
		categoryCache.removeAll();
	}
	
	@After("execution(public * blog.service.impl.CategoryServiceImpl.addCategory(..))")
	public void afterAddCategory(JoinPoint jp){
		categoryCache.removeAll();
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
