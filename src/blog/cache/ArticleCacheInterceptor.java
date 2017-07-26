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

import blog.model.Article;
import blog.util.CacheUtil;

/** 
 * 文章相关缓存AOP类
 * @author zjz
 */
@Aspect
@Component
public class ArticleCacheInterceptor {
	private CacheUtil cacheUtil;
	private Cache articleCache;
	private Cache hotArticleCache;
	
	@PostConstruct
	public void init(){
		articleCache = cacheUtil.getCache("articleCache");
		hotArticleCache = cacheUtil.getCache("hotArticleCache");
	}
	
	/**
	 * 获取热门列表
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(public * blog.service.impl.ArticleServiceImpl.getHotArticleListInCache(..))")
	public Object aroundGetHotArticleList(ProceedingJoinPoint jp) throws Throwable{
		int categoryId = (Integer)jp.getArgs()[0];
		return cacheUtil.executeAroundMethod(jp, hotArticleCache, categoryId);
	}
	
	/**
	 * 获取正文信息
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(public * blog.dao.impl.ArticleDaoImpl.getContent(..))")
	public Object aroundGetArticle(ProceedingJoinPoint jp) throws Throwable{
		int articleId = (Integer)jp.getArgs()[0];
		return cacheUtil.executeAroundMethod(jp, articleCache, articleId);  
	}
	
	@After("execution(public * blog.service.impl.ArticleServiceImpl.deleteArticle(..))")
	public void afterDeleteArticle(JoinPoint jp){
		int articleId = (Integer)jp.getArgs()[0];
		articleCache.remove(articleId);
	}
	
	@After("execution(public * blog.service.impl.ArticleServiceImpl.updateArticle(..))")
	public void afterUpdateArticle(JoinPoint jp){
		Article article = (Article)jp.getArgs()[0];
		articleCache.remove(article.getId());
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
