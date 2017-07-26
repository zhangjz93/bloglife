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
 * 通知提醒缓存AOP类
 * @author zjz
 */
@Aspect
@Component
public class NoticeCacheInterceptor{
	private CacheUtil cacheUtil;
	private Cache noticeCache;
	
	@PostConstruct
	public void init(){
		noticeCache = cacheUtil.getCache("noticeCache");
	}
	
	/**
	 * 获取未读消息数
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(public * blog.service.impl.NoticeServiceImpl.getUnreadNoticeNum(..))")
	public Object aroundGetUnreadNoticeNum(ProceedingJoinPoint jp) throws Throwable{
		int userId = (Integer)jp.getArgs()[0];
		return cacheUtil.executeAroundMethod(jp, noticeCache, userId);
	}
	
	/**
	 * 添加通知后删除对应用户缓存
	 * @param jp
	 */
	@After("execution(public * blog.dao.impl.ReplyDaoImpl.addNotice(..))")
	public void afterAddNotice(JoinPoint jp){
		int userId = (Integer)jp.getArgs()[0]; 
		noticeCache.remove(userId);
	}
	
	/**
	 * 获取通知列表后删除对应用户缓存
	 * @param jp
	 */
	@After("execution(public * blog.service.impl.NoticeServiceImpl.getNoticeList(..))")
	public void afterGetNoticeList(JoinPoint jp){
		int userId = (Integer)jp.getArgs()[0];
		noticeCache.remove(userId);
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
