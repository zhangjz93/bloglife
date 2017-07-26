package blog.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import blog.enumeration.ErrorEnum;

/** 
 * BaseController，定义错误信息及简化操作
 * @author zjz
 */
@Controller
public class BaseController {
	private static final String ERROR_PAGE = "jsp/error.jsp";
	
	/**
	 * 跳转至统一的错误页面
	 * @param req
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String jumpToErrorPage(HttpServletRequest req, ErrorEnum errorEnum){
		if(errorEnum.equals(errorEnum.FORMAT)){
			req.setAttribute("errorMsg", "格式不正确！");
		}else if(errorEnum.equals(errorEnum.EXIST)){
			req.setAttribute("errorMsg", "请求的资源不存在！");
		}else if(errorEnum.equals(errorEnum.SESSION_EXIST)){
			req.setAttribute("errorMsg", "请不要重复登陆！");
		}else{
			req.setAttribute("errorMsg", "");
		}
		return ERROR_PAGE;
	}
	
	/**
	 * 获取PrintWriter
	 * @param res
	 * @return
	 */
	public PrintWriter getWriter(HttpServletResponse res){
		PrintWriter pw = null;
		try {
			pw = res.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pw;
	}
	
	/**
	 * 将输出信息写入到输出流
	 * @param flag
	 * @param res
	 */
	public void writeMessage(boolean flag, HttpServletResponse res){
		PrintWriter pw = getWriter(res);
		pw.print(flag);
	}
	
	/**
	 * 将输出信息写入到输出流
	 * @param message
	 * @param res
	 */
	public void writeMessage(String message, HttpServletResponse res){
		PrintWriter pw = getWriter(res);
		pw.print(message);
	}
}
