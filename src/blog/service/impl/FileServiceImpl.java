package blog.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import blog.dao.FileDao;
import blog.dao.UserDao;
import blog.model.Attach;
import blog.service.FileService;
import blog.util.PropertiesLoader;

/** 
 * FileService实现类
 * @author zjz
 */
@Service("fileService")
public class FileServiceImpl implements FileService {
	private static final String[] ALLOW_SUFFIX = {".jpg",".png",".gif",".bmp",".jpeg"};  //允许的格式
	private static DateFormat df_folder; //文件夹命名日期格式
	private static DateFormat df_file;  //文件命名日期格式
	private static Random random;
	
	private PropertiesLoader config;
	private UserDao userDao;
	private FileDao fileDao;
	
	static{
		df_folder = new SimpleDateFormat("yyyy-MM-dd");
		df_file = new SimpleDateFormat("yyyyMMddHHmmss");
		random = new Random();
	}
	
	/**
	 * @see blog.service.FileService#uploadFile(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String)
	 */
	public String uploadFile(MultipartFile file, String projName, String path){
		String fname = file.getOriginalFilename();  //原始文件名
		String suffix = fname.substring(fname.lastIndexOf("."), fname.length());  //后缀
		if(!examType(suffix, ALLOW_SUFFIX)){
			return getInfo(1, "图片格式不正确！");
		}
		if(file.getSize() > config.getLong("attach.maxSize")){
			return getInfo(1, "图片超过指定文件大小！");
		}
		//重命名并上传 
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String rint = String.valueOf(random.nextInt(1000)); 
		String folderName = df_folder.format(now);
		String fileName = df_file.format(now) + rint + suffix;  //保存的文件名
		File folder = new File(path, folderName);  //文件夹目录
		if(!folder.exists()){
			folder.mkdir();  //若不存在则创建
		}
		File finalPath = new File(folder, fileName);
		try {
			file.transferTo(finalPath);  //开始上传
		} catch (IllegalStateException e) {
			System.out.println("上传失败！");
		} catch (IOException e) {
			System.out.println("上传失败！");
		}
		return getInfo(0, projName + "/upload/" + folderName + "/" + fileName);  //返回文件路径信息
	} 
	
	/**
	 * @see blog.service.FileService#uploadPhoto(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String, int)
	 */
	public String uploadPhoto(MultipartFile file, String projName, String path, int uid){
		String fname = file.getOriginalFilename();  //原始文件名
		String suffix = fname.substring(fname.lastIndexOf("."), fname.length());  //后缀
		if(!examType(suffix, ALLOW_SUFFIX)){
			return getInfo(1, "格式不正确！");
		}
		if(file.getSize() > config.getLong("photo.maxSize")){
			return getInfo(1, "图片超过指定大小！");
		}
		String fileName = "photo" + uid + suffix;  //重命名
		File savePath = new File(path, fileName);
		try {
			file.transferTo(savePath);  
		} catch (IllegalStateException e) {
			System.out.println("上传失败！");
		} catch (IOException e) {
			System.out.println("上传失败！");
		}
		//更新用户头像路径
		String absolutePath = projName + "/upload/photo/" + fileName;
		userDao.updatePhoto(uid, absolutePath);
		return getInfo(0, absolutePath);  //返回头像保存路径信息
	}
	
	/** 
	 * @see blog.service.FileService#updateCategoryIcon(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String)
	 */
	public String updateCategoryIcon(MultipartFile file, String projName, String path){
		String fname = file.getOriginalFilename();  //原始文件名
		String suffix = fname.substring(fname.lastIndexOf("."), fname.length());  //后缀
		if(!examType(suffix, ALLOW_SUFFIX)){
			return getInfo(1, "格式不正确！");
		}
		if(file.getSize() > config.getLong("categoryIcon.maxSize")){
			return getInfo(1, "图片超过指定大小！");
		}
		File savePath = new File(path, fname);
		try {
			file.transferTo(savePath);  
		} catch (IllegalStateException e) {
			System.out.println("上传失败！");
		} catch (IOException e) {
			System.out.println("上传失败！");
		}
		return getInfo(0, projName + "/resources/" + fname);
	}
	
	/**
	 * @see blog.service.FileService#cleanAttachesOfDate(java.sql.Date)
	 */
	@Transactional(readOnly=true)
	public void cleanAttachesOfDate(Date date){
		List<Attach> attaches = fileDao.getAttachesOfDate(date);
		Set<Attach> attachesSet = new HashSet<Attach>(attaches);  //将附件List转换为Set
		String scanFolder = System.getProperty("webApp.root") + "upload" + File.separator + date.toString();  //需要扫描的文件夹
		File folder = new File(scanFolder);
		if(folder.exists()){
			File[] files = folder.listFiles();  //文件列表
			for(File file : files){
				Attach attach = new Attach();
				attach.setPath(file.getName());
				if(!attachesSet.contains(attach)){
					file.delete();  //删除
				}
			}
		}else{
			return;
		}
	}
	
	/**
	 * 判断是否为指定的文件类型
	 * @param suffix
	 * @param allow
	 * @return
	 */
	private boolean examType(String suffix, String[] allowance){
		for(int i=0; i<allowance.length; i++){
			if(suffix.equals(allowance[i])){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 返回JSON信息，上传失败则返回key为1的JSON对象
	 * @param flag
	 * @param tip
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getInfo(int flag, String tip){
		JSONObject json = new JSONObject();
		if(flag == 0){
			json.put("error", 0);
			json.put("url", tip);
		}else{
			json.put("error", 1);
			json.put("message", tip);
		}
		return json.toJSONString();
	}
	
	//getters and setters
	public UserDao getUserDao() {
		return userDao;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PropertiesLoader getConfig() {
		return config;
	}
	
	@Resource(name="config")
	public void setConfig(PropertiesLoader config) {
		this.config = config;
	}

	public FileDao getFileDao() {
		return fileDao;
	}
	
	@Resource(name="fileDao")
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}
}
