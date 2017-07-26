package blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dao.ArticleDao;
import blog.dao.CategoryDao;
import blog.dao.CollectDao;
import blog.dao.DynamicDao;
import blog.dao.FileDao;
import blog.dao.UserDao;
import blog.enumeration.ValueConstraint;
import blog.model.Article;
import blog.model.Category;
import blog.model.Dynamic;
import blog.service.ArticleService;
import blog.service.ReplyService;
import blog.service.UserService;
import blog.util.PageUtil;
import blog.util.PropertiesLoader;
import blog.util.TextUtil;
import blog.util.ValidationUtil;

/** 
 * ArticleService实现类
 * @author zjz
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
	private PropertiesLoader config;
	private ArticleDao articleDao;
	private CategoryDao categoryDao;
	private UserDao userDao;
	private FileDao fileDao;
	private CollectDao collectDao;
	private UserService userService;
	private ReplyService replyService;
	private DynamicDao dynamicDao;
	private ArticleService articleService;
		
	/**
	 * 获取文章列表及个人基本信息
	 * @see blog.service.ArticleService#getArticleList(int, int)
	 */
	@Transactional(readOnly=true)
	public List<Article> getArticleList(int cid, int page){
		int pageSize = config.getInt("article.person.pageSize");
		int start = (page-1) * pageSize;
		List<Article> articles = articleDao.getArticleList(cid, start, pageSize);
		for(int i=0; i<articles.size(); i++){
			Article article = articles.get(i);
			article.setWriter(userDao.getUserById(article.getWriter().getId()));  //用户个人信息
		}
		return articles;
	}
	
	/**
	 * @see blog.service.ArticleService#getArticleList(int, java.lang.String)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getArticleList(int page, String searchTitle){
		Map<String,Object> map = new HashMap<String,Object>();
		int pageSize = config.getInt("article.admin.pageSize");
		int start = (page-1) * pageSize;
		String utf8Str = "".equals(searchTitle) ? "" : TextUtil.encodingStr(searchTitle, "UTF-8");//转码
		List<Article> articles = articleDao.getArticleList(start, pageSize, utf8Str);
		for(int i=0; i<articles.size(); i++){
			Article article = articles.get(i);
			article.setWriter(userDao.getUserById(article.getWriter().getId()));  //用户信息
			article.setCategory(categoryDao.getCategory(article.getCategory().getId()));  //类别信息
		}
		int articleNum = articleDao.getArticleNum(utf8Str);
		int totalPage = PageUtil.getTotalPage(articleNum, pageSize);
		map.put("articles", articles);
		map.put("totalPage", totalPage);
		map.put("articleNum", articleNum);
		map.put("search", utf8Str);
		return map;
	}

	/**
	 * @see blog.service.ArticleService#getRecommendList(int)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getRecommendList(int page){
		Map<String,Object> map = new HashMap<String,Object>();
		int pageSize = config.getInt("article.admin.pageSize");
		int start = (page-1) * pageSize;
		List<Article> articles = articleDao.getRecommendList(start, pageSize);
		for(int i=0; i<articles.size(); i++){
			Article article = articles.get(i);
			article.setWriter(userDao.getUserById(article.getWriter().getId()));  //用户信息
			article.setCategory(categoryDao.getCategory(article.getCategory().getId()));  //类别信息
		}
		int articleNum = articleDao.getRecommendNum();
		int totalPage = PageUtil.getTotalPage(articleNum, pageSize);
		map.put("articles", articles);
		map.put("totalPage", totalPage);
		map.put("articleNum", articleNum);
		return map;
	}
	
	/**
	 * @see blog.service.ArticleService#getRecommendList()
	 */
	@Transactional(readOnly=true)
	public List<Article> getRecommendList(){
		List<Article> articles = articleDao.getRecommendList(0, config.getInt("article.goodNum"));
		for(int i=0; i<articles.size(); i++){
			Article article = articles.get(i);
			article.setWriter(userDao.getUserById(article.getWriter().getId()));  //用户信息
			article.setCategory(categoryDao.getCategory(article.getCategory().getId()));  //类别信息
		}
		return articles;
	}
	
	/**
	 * @see blog.service.ArticleService#getCategoryAndArticleList(int, int)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getCategoryAndArticleList(int cid, int page){
		Map<String,Object> map = new HashMap<String,Object>();
		int pageSize = config.getInt("article.pageSize");
		Category category = categoryDao.getCategory(cid);
		if(category == null)
			return null;
		List<Article> articles = getArticleList(cid, page);  //列表
		int articleNum = articleDao.getArticleNum(cid);
		int totalPage = PageUtil.getTotalPage(articleNum, pageSize);
		List<Article> hotArticles = articleService.getHotArticleListInCache(cid);  //热门列表，这里为防止AOP失效，注入了自身对象
		map.put("category", category);
		map.put("articles", articles);
		map.put("totalPage", totalPage);
		map.put("articleNum", articleNum);
		map.put("hotArticles", hotArticles);
		return map;
	}
	
	/**
	 * @see blog.service.ArticleService#getArticleListOfUser(int, int, int)
	 */
	@Transactional(readOnly=true)
	public Map<String,Object> getArticleListOfUser(int uid, int visitorId, int page){
		Map<String,Object> userMap = userService.getUserInfo(uid, visitorId);  //用户名片信息
		int pageSize = config.getInt("article.person.pageSize");
		if(userMap == null)
			return null;
		Map<String,Object> map = new HashMap<String,Object>();
		int start = (page-1) * pageSize;
		List<Article> articles = articleDao.getArticleListOfUser(uid, start, pageSize);
		for(int i=0; i<articles.size(); i++){
			Article article = articles.get(i);
			article.setCategory(categoryDao.getCategory(article.getCategory().getId()));  //类别信息
		}
		int articleNum = articleDao.getArticleNumOfUser(uid);
		int totalPage = PageUtil.getTotalPage(articleNum, pageSize);
		map.put("userMap", userMap);
		map.put("articles", articles);
		map.put("articleNum", articleNum);
		map.put("totalPage", totalPage);
		return map;
	}
	
	/**
	 * @see blog.service.ArticleService#getArticle(int, int)
	 */
	@Transactional
	public Map<String,Object> getArticle(int aid, int visitorId){
		Map<String,Object> map = new HashMap<String,Object>();
		Article article = articleDao.getArticle(aid); 
		if(article == null)  
			return null;
		article.setContent(articleDao.getContent(article.getId()));  //正文
		article.setWriter(userDao.getUserById(article.getWriter().getId()));  //作者
		article.setCategory(categoryDao.getCategory(article.getCategory().getId()));  //类别
		map.put("article", article);
		//获取第一页回复
		Map<String,Object> replyMap = replyService.getReplyList(aid, 1, false);
		map.put("replies", replyMap.get("replies"));  
		map.put("replyNum", replyMap.get("replyNum")); 
		//是否被用户收藏
		boolean isCollect = collectDao.isUserCollect(visitorId, aid);  //查询是否被收藏
		map.put("isCollect", isCollect);
		articleDao.addClickNum(aid, config.getInt("article.topClickNum"));  //增加点击
		return map;
	}
	
	/**
	 * @see blog.service.ArticleService#getArticleOfUser(int, int)
	 */
	@Transactional(readOnly=true)
	public Article getArticleOfUser(int aid, int wid){
		Article article = articleDao.getArticle(aid, wid);
		if(article == null)
			return null;
		article.setContent(articleDao.getContent(article.getId()));  //正文
		article.setCategory(categoryDao.getCategory(article.getCategory().getId()));  //类别
		return article;
	}
	
	/**
	 * @see blog.service.ArticleService#addArticle(blog.model.Article)
	 */
	@Transactional
	public boolean addArticle(Article article){
		if(articleValidation(article)){
			String content = article.getContent().getWords();  //原始内容
			article.getContent().setWords(Jsoup.clean(content, TextUtil.ArticleAllowTags()));  //保留HTML标签，过滤非法脚本
			//自动生成摘要
			if(ValidationUtil.isBlank(article.getSummary())){
				String filteredContent = TextUtil.HtmlFormatFilter(article.getContent().getWords());
				String summary = filteredContent.length()<ValueConstraint.MAX_ARTICLE_SUMMARY ? filteredContent : filteredContent.substring(0, ValueConstraint.MAX_ARTICLE_SUMMARY);
				article.setSummary(summary + "...");
			}
			int articleId = articleDao.addArticle(article);  //文章id
			article.setId(articleId);
			List<String> paths = TextUtil.convertImgToPaths(article.getContent().getWords()); 
			fileDao.addPaths(articleId, paths);  //附件路径
			//添加动态
			Dynamic dynamic = new Dynamic();
			dynamic.setArticle(article);
			dynamic.setType(1);
			dynamic.setUser(article.getWriter());
			dynamicDao.addDynamic(dynamic);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @see blog.service.ArticleService#updateArticle(blog.model.Article)
	 */
	@Transactional
	public boolean updateArticle(Article article){
		if(articleValidation(article)){
			//自动生成摘要
			if(ValidationUtil.isBlank(article.getSummary())){
				String filteredContent = TextUtil.HtmlFormatFilter(article.getContent().getWords().trim());
				String summary = filteredContent.length()<ValueConstraint.MAX_ARTICLE_SUMMARY ? filteredContent : filteredContent.substring(0, ValueConstraint.MAX_ARTICLE_SUMMARY);
				article.setSummary(summary + "...");
			}
			articleDao.updateArticle(article);
			fileDao.deletePaths(article.getId());
			List<String> paths = TextUtil.convertImgToPaths(article.getContent().getWords()); 
			fileDao.addPaths(article.getId(), paths); 
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * @see blog.service.ArticleService#deleteArticle(int, int)
	 */
	@Transactional
	public boolean deleteArticle(int aid, int wid){
		articleDao.deleteArticle(aid, wid);
		return true;
	}
	
	@Transactional
	public boolean deleteArticle(int aid){
		articleDao.deleteArticle(aid);
		return true;
	}
	
	/**
	 * @see blog.service.ArticleService#getHotArticleList(int)
	 */
	@Transactional(readOnly=true)
	public List<Article> getHotArticleList(int cid){
		return articleDao.getHotArticleList(cid, config.getInt("article.hotNum"));
	}
	
	/**
	 * @see blog.service.ArticleService#getNewArticleList()
	 */
	@Transactional(readOnly=true)
	public List<Article> getNewArticleList(){
		return articleDao.getNewArticleList(config.getInt("article.newNum"));
	}
	
	/**
	 * @see blog.service.ArticleService#getHotArticleListInCache(int)
	 */
	@Transactional(readOnly=true)
	public List<Article> getHotArticleListInCache(int cid){
		return getHotArticleList(cid);
	}
	
	/**
	 * @see blog.service.ArticleService#addRecommend(int)
	 */
	@Transactional
	public boolean addRecommend(int aid){
		articleDao.addRecommend(aid);
		return true;
	}
	
	/**
	 * @see blog.service.ArticleService#deleteRecommend(int)
	 */
	@Transactional
	public boolean deleteRecommend(int aid){
		articleDao.deleteRecommend(aid);
		return true;
	}
	
	/**
	 * 验证文章编辑表单
	 * @param article
	 * @return
	 */
	private boolean articleValidation(Article article){
		return ValidationUtil.isText(article.getTitle(), ValueConstraint.MIN_ARTICLE_TITLE, ValueConstraint.MAX_ARTICLE_TITLE) && 
				ValidationUtil.isText(article.getSummary(), ValueConstraint.MIN_ARTICLE_SUMMARY, ValueConstraint.MAX_ARTICLE_SUMMARY);
	}
	
	//getters and setters
	public ArticleDao getArticleDao() {
		return articleDao;
	}
	
	@Resource(name="articleDao")
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	
	@Resource(name="categoryDao")
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public FileDao getFileDao() {
		return fileDao;
	}
	
	@Resource(name="fileDao")
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public CollectDao getCollectDao() {
		return collectDao;
	}
	
	@Resource(name="collectDao")
	public void setCollectDao(CollectDao collectDao) {
		this.collectDao = collectDao;
	}

	public UserService getUserService() {
		return userService;
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public ReplyService getReplyService() {
		return replyService;
	}
	
	@Resource(name="replyService")
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}

	public PropertiesLoader getConfig() {
		return config;
	}
	
	@Resource(name="config")
	public void setConfig(PropertiesLoader config) {
		this.config = config;
	}

	public ArticleService getArticleService() {
		return articleService;
	}
	
	@Resource(name="articleService")
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public DynamicDao getDynamicDao() {
		return dynamicDao;
	}
	
	@Resource(name="dynamicDao")
	public void setDynamicDao(DynamicDao dynamicDao) {
		this.dynamicDao = dynamicDao;
	}
}
