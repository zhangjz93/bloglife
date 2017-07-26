package blog.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/** 
 * 异常处理，将错误信息记录并跳转
 * @author zjz
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver{
	private static Logger log = Logger.getLogger("errorLog");

	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object obj, Exception ex) {
		ModelAndView mv = new ModelAndView("jsp/exception.jsp");
		log.error("发生异常:", ex);
		return mv;
	}
	
}
