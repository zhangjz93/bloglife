package blog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.model.User;
import blog.service.FileService;

/** 
 * 附件保存Controller
 * @author zjz
 */
@Controller
public class FileController extends BaseController{
	private FileService fileService;
	
	/**
	 * 图片上传并返回JSON
	 * @param res
	 * @param req
	 * @param file
	 */
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void fileUpload(HttpServletResponse res, HttpServletRequest req, @RequestParam("imgFile")MultipartFile file){
		res.setContentType("text/html; charset=UTF-8");
		String path = req.getSession().getServletContext().getRealPath("/upload");  //绝对路径
		String projName = req.getContextPath();
		String json = fileService.uploadFile(file, projName, path);  //上传
		writeMessage(json, res);
	}
	
	/**
	 * 上传头像并返回JSON
	 * @param res
	 * @param req
	 * @param file
	 */
	@RequestMapping(value="/uploadPhoto", method=RequestMethod.POST)
	public void photoUpload(HttpServletResponse res, HttpServletRequest req, @RequestParam("photo")MultipartFile file){
		User user = (User)req.getSession().getAttribute("user");
		res.setContentType("text/html; charset=UTF-8");
		String path = req.getSession().getServletContext().getRealPath("/upload/photo");
		String projName = req.getContextPath();
		String json = fileService.uploadPhoto(file, projName, path, user.getId());  
		writeMessage(json, res);
	}
	
	/**
	 * 类别图标上传
	 * @param res
	 * @param req
	 * @param file
	 */
	@RequestMapping(value="/admin/uploadIcon", method=RequestMethod.POST)
	public void categoryIconUpload(HttpServletResponse res, HttpServletRequest req, @RequestParam("icon")MultipartFile file){
		res.setContentType("text/html; charset=UTF-8");
		String path = req.getSession().getServletContext().getRealPath("/resources");
		String projName = req.getContextPath();
		String json = fileService.updateCategoryIcon(file, projName, path);
		writeMessage(json, res);
	}
	
	//getters and setters
	public FileService getFileService() {
		return fileService;
	}
	
	@Resource(name="fileService")
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
}
