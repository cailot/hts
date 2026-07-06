package au.org.hts.dashboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import au.org.hts.dashboard.util.HumeDashboardConstants;

@Controller
public class ExceptionController implements ErrorController{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	// it triages the error condition and forward to the proper error code jsp
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model map) {
		Integer statusCode = (Integer) request.getAttribute(HumeDashboardConstants.ERROR_STATUS);
		Exception exception = (Exception) request.getAttribute(HumeDashboardConstants.EXCEPTION);
		log.info("Http status code >> " + statusCode);
		log.info("Exception >> " + exception);
		
		// extra error info
		Class<?> exceptionType = (Class<?>) request.getAttribute(HumeDashboardConstants.EXCEPTION_TYPE);
		String errorMessage = (String) request.getAttribute(HumeDashboardConstants.ERROR_MESSAGE);
		String requestUri = (String) request.getAttribute(HumeDashboardConstants.ERROR_URI);
		String servletName = (String) request.getAttribute(HumeDashboardConstants.SERVLET_NAME);
		log.info("exceptionType >> " + exceptionType);
		log.info("errorMessage >> " + errorMessage);
		log.info("requestUri >> " + requestUri);
		log.info("servletName >> " + servletName);
		
		map.addAttribute(HumeDashboardConstants.STATUS_CODE, statusCode);
		
		String returnTo = "";
		if(statusCode == 403) {
			returnTo = "error/403";
		}else if(statusCode == 404) {
			returnTo = "error/404";
		}else if(statusCode == 500) {
			returnTo = "error/500";
		}
		return returnTo;
		
	}	
}
