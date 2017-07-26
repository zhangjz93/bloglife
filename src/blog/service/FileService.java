package blog.service;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

/** 
 * 文件上传管理Service接口
 * @author zjz
 */
public interface FileService {
	
	/**
	 * 上传文件，返回json
	 * @param file
	 * @param path 保存的路径
	 * @param projName 项目名
	 * @return
	 */
	public String uploadFile(MultipartFile file, String projName, String path);
	
	/**
	 * 上传头像
	 * @param file
	 * @param projName 项目名
	 * @param path 保存路径
	 * @param uid 用户id
	 * @return
	 */
	public String uploadPhoto(MultipartFile file, String projName, String path, int uid);
	
	/**
	 * 上传类别图标
	 * @param file
	 * @param projName
	 * @param path
	 * @return
	 */
	public String updateCategoryIcon(MultipartFile file, String projName, String path);
	
	/**
	 * 清理不使用的附件资源
	 * @param date 清理该日期内的附件资源
	 */
	public void cleanAttachesOfDate(Date date);
}
