package blog.cron;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import blog.model.Article;
import blog.model.Category;
import blog.service.ArticleService;
import blog.service.CategoryService;
import blog.service.FileService;
import blog.util.CacheUtil;

/** 
 * 定时任务类
 * @author zjz
 */
@Component("cronJob")
public class CronJob {
	private CacheUtil cacheUtil;
	private ArticleService articleService;
	private CategoryService categoryService;
	private FileService fileService;
	private static Logger log = Logger.getLogger("infoLog"); 
	
	/**
	 * 将排行结果依次存入缓存
	 */
	@Scheduled(cron="0 0 0/1 * * ?")
	public void updateHotArticleList(){
		System.out.println("------更新热门排行开始");
		log.info("------更新热门排行开始------");
		Cache hotArticleCache = cacheUtil.getCache("hotArticleCache");
		List<Category> categories = categoryService.getCategoryList();
		for(int i=0; i<categories.size(); i++){
			int categoryId = categories.get(i).getId();
			List<Article> hotArticles = articleService.getHotArticleList(categoryId);
			hotArticleCache.remove(categoryId);
			cacheUtil.put(hotArticleCache, categoryId, hotArticles);
		}
		log.info("------更新热门排行结束------");
		System.out.println("------更新完毕");
	}
	
	/**
	 * 清理昨日附件
	 */
	@Scheduled(cron="0 0 3 * * ?")
	public void cleanAttaches(){
		System.out.println("------清理附件开始");
		log.info("------附件清理开始------");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date yesterday = new Date(calendar.getTimeInMillis());  
		fileService.cleanAttachesOfDate(yesterday);
		log.info("------附件清理结束------");
		System.out.println("------清理附件完毕");
	}
	
	//getters and setters
	public CacheUtil getCacheUtil() {
		return cacheUtil;
	}
	
	public ArticleService getArticleService() {
		return articleService;
	}
	
	@Resource(name="cacheUtil")
	public void setCacheUtil(CacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
	
	@Resource(name="articleService")
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}
	
	@Resource(name="categoryService")
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public FileService getFileService() {
		return fileService;
	}
	
	@Resource(name="fileService")
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
}
