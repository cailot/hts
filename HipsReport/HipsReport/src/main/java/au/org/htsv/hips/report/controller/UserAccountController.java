package au.org.htsv.hips.report.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
//import java.util.logging.Logger;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import au.org.htsv.hips.report.entity.ExceptionBasicData;
import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.entity.UserData;
import au.org.htsv.hips.report.service.UserAccountService;
import au.org.htsv.hips.report.service.ExceptionReportService;
import au.org.htsv.hips.report.util.ExceptionReportConstants;
import au.org.htsv.hips.report.util.ExceptionReportUtils;

/**
 * @author js278
 *
 */
@Controller
public class UserAccountController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserAccountController.class);

	@Autowired
	private UserAccountService userAccountService;
	
	// show user (or users)
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String list(ModelMap modelMap) {
		
		Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		String acronym = null;
		for(GrantedAuthority auth: auths) {
			acronym = auth.getAuthority();
			acronym = StringUtils.removeStartIgnoreCase(acronym, ExceptionReportConstants.ROLE_PREFIX);
		}
		// in case of Admin, list all users
		if(StringUtils.equalsIgnoreCase(ExceptionReportConstants.ADMINISTRATOR, acronym)) {
			List<UserData> users = userAccountService.getAllUsers();
			modelMap.put(ExceptionReportConstants.USER_LIST, users);
			
//			Map<String, String> roles = new LinkedHashMap<String, String>();
//			roles.put("ALL", "Administrator");
//			roles.put("US", "United Stated");
//			roles.put("AS", "Australia");
//			modelMap.put("roleList", roles);
		}
		return "userPage";
	}
	

	// register new user
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String add(HttpServletRequest request) {
		UserData user = new UserData();
		user.setUsername(request.getParameter(ExceptionReportConstants.USER_NAME + ExceptionReportConstants.ADD).trim());
		user.setFirstname(request.getParameter(ExceptionReportConstants.FIRST_NAME + ExceptionReportConstants.ADD).trim());
		user.setLastname(request.getParameter(ExceptionReportConstants.LAST_NAME + ExceptionReportConstants.ADD).trim());
		user.setPassword(request.getParameter(ExceptionReportConstants.PASSWORD + ExceptionReportConstants.ADD).trim());
		//user.setPassword("Today123");
		user.setRole(ExceptionReportConstants.ROLE_PREFIX + request.getParameter(ExceptionReportConstants.ROLE + ExceptionReportConstants.ADD).trim());
		int result = userAccountService.addUser(user);
		//if(result==0) already exists
		return "redirect:user";
	}
	
	// suspend user
	@RequestMapping(value = "/suspendUser", method = RequestMethod.POST)
	public String suspend(HttpServletRequest request) {
		String username = request.getParameter(ExceptionReportConstants.USER_NAME + ExceptionReportConstants.SUSPEND).trim();
		int result = userAccountService.suspendUser(username);
		//if(result==0) already exists
		return "redirect:user";
	}
	
	// activate user
	@RequestMapping(value = "/activateUser", method = RequestMethod.POST)
	public String activate(HttpServletRequest request) {
		String username = request.getParameter(ExceptionReportConstants.USER_NAME + ExceptionReportConstants.ACTIVATE).trim();
		int result = userAccountService.activateUser(username);
		//if(result==0) already exists
		return "redirect:user";
	}
	
	// delete user
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String delete(HttpServletRequest request) {
		String username = request.getParameter(ExceptionReportConstants.USER_NAME + ExceptionReportConstants.DELETE).trim();
		int result = userAccountService.deleteUser(username);
		//if(result==0) already exists
		return "redirect:user";
	}
	
	// edit user
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public String edit(HttpServletRequest request) {
		String username = request.getParameter(ExceptionReportConstants.USER_NAME + ExceptionReportConstants.EDIT).trim();
		UserData user = userAccountService.getUser(username);
		user.setFirstname(request.getParameter(ExceptionReportConstants.FIRST_NAME + ExceptionReportConstants.EDIT).trim());
		user.setLastname(request.getParameter(ExceptionReportConstants.LAST_NAME + ExceptionReportConstants.EDIT).trim());
		user.setRole(ExceptionReportConstants.ROLE_PREFIX + request.getParameter(ExceptionReportConstants.ROLE + ExceptionReportConstants.EDIT).trim());
		int result = userAccountService.modifyUser(user);
		//if(result==0) already exists
		return "redirect:user";
	}
	
	// edit user
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String passwordUpdate(HttpServletRequest request) {
		String username = request.getParameter(ExceptionReportConstants.USER_NAME + ExceptionReportConstants.PASSWORD).trim();
		String password = request.getParameter(ExceptionReportConstants.PASSWORD + ExceptionReportConstants.PASSWORD).trim();
		UserData user = new UserData();
		user.setUsername(username);
		user.setPassword(password);
		int result = userAccountService.updatePassword(user);
		//if(result==0) already exists
		return "redirect:user";
	}
		
}
