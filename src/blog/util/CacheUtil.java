package blog.util;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

/** 
 * 关于缓存操作的辅助类
 * @author zjz
 */
@Component("cacheUtil")
public class CacheUtil {
	private EhCacheCacheManager ehManager;
	
	/**
	 * 根据名称获取缓存
	 * @param name
	 * @return
	 */
	public Cache getCache(String name){
		CacheManager cm = ehManager.getCacheManager();
		return cm.getCache(name);
	}
	
	/**
	 * 获取缓存对象
	 * @param cache
	 * @param key
	 * @return 不存在映射关系则返回null
	 */
	public Object getObject(Cache cache, Object key){
		if(cache.isElementInMemory(key) && cache.get(key)!=null){
			return cache.get(key).getObjectValue();
		}else{
			return null;
		}
	}
	
	/**
	 * 存入缓存
	 * @param cache
	 * @param key
	 * @param value
	 */
	public void put(Cache cache, Object key, Object value){
		cache.put(new Element(key, value));
	}
	
	/**
	 * 方法执行，若缓存中不存在该项则执行方法并存入缓存
	 * @param jp
	 * @param cache
	 * @param key
	 * @return 方法执行结果
	 * @throws Throwable 
	 */
	public Object executeAroundMethod(ProceedingJoinPoint jp, Cache cache, Object key) throws Throwable{
		Object result = getObject(cache, key);
		if(result == null){
			result = jp.proceed();
			cache.put(new Element(key, result));
			return result;
		}
		return result;
	}
	
	/**
	 * 方法执行后清除缓存
	 * @param cache
	 * @param key
	 */
	public void removeAfterMethod(Cache cache, Object key){
		if(cache.isElementInMemory(key))
			cache.remove(key);
	}
	
	//getters and setters
	public EhCacheCacheManager getEhManager() {
		return ehManager;
	}
	
	@Resource(name="cacheManager")
	public void setEhManager(EhCacheCacheManager ehManager) {
		this.ehManager = ehManager;
	}
}
