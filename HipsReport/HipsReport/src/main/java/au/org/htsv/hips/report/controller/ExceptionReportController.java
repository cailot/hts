package au.org.htsv.hips.report.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import au.org.htsv.hips.report.entity.ExceptionBasicData;
import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.entity.HpiiDTO;
import au.org.htsv.hips.report.service.ExceptionReportService;
import au.org.htsv.hips.report.util.ExceptionReportConstants;
import au.org.htsv.hips.report.util.ExceptionReportUtils;

/**
 * @author js278
 *
 */
@Controller
public class ExceptionReportController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionReportController.class);

	@Autowired
	private ExceptionReportService exceptionReportService;

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String uploads(@RequestParam(value="searchCheck", required=false, defaultValue="false") boolean checked, @RequestParam(value="siteName", required=false) String siteName, @RequestParam(value="fromDate", required=false) String from, @RequestParam(value="toDate", required=false) String to, HttpSession session) {
		if(checked) {
			session.setAttribute(ExceptionReportConstants.FROM_DATE, from);
			session.setAttribute(ExceptionReportConstants.TO_DATE, to);
			session.setAttribute(ExceptionReportConstants.DASHBOARD_FROM_DATE, from);
			session.setAttribute(ExceptionReportConstants.DASHBOARD_TO_DATE, to);
			session.setAttribute(ExceptionReportConstants.DASHBOARD_HOSPITAL, siteName);
			ExceptionBasicData data = new ExceptionBasicData();
			data.setFromDate(ExceptionReportUtils.ddMMyyyy2yyyyMMdd(from));
			data.setToDate(ExceptionReportUtils.ddMMyyyy2yyyyMMdd(to));
			data.setFacility(getFacilityCode(siteName, session));
			session.setAttribute(ExceptionReportConstants.DASHBOARD_DATA, exceptionReportService.getDashboard(data));
		}else {
			Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			String acronym = null;
			for(GrantedAuthority auth: auths) {
				acronym = auth.getAuthority();
				acronym = StringUtils.removeStartIgnoreCase(acronym, ExceptionReportConstants.ROLE_PREFIX);
			}
//			logger.finest("User belongs to " + acronym);
			setHospitals(acronym, session);
		}
		return "dashboardPage";
	}
	
	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public String stats(@RequestParam(value="searchCheck", required=false, defaultValue="false") boolean checked, @RequestParam(value="siteName", required=false) String siteName, @RequestParam(value="fromDate", required=false) String from, @RequestParam(value="toDate", required=false) String to, HttpSession session) {
		if(checked) {
			session.setAttribute(ExceptionReportConstants.FROM_DATE, from);
			session.setAttribute(ExceptionReportConstants.TO_DATE, to);
			session.setAttribute(ExceptionReportConstants.SUMMARY_FROM_DATE, from);
			session.setAttribute(ExceptionReportConstants.SUMMARY_TO_DATE, to);
			session.setAttribute(ExceptionReportConstants.SUMMARY_HOSPITAL, siteName);
			ExceptionBasicData data = new ExceptionBasicData();
			data.setFromDate(ExceptionReportUtils.ddMMyyyy2yyyyMMdd(from));
			data.setToDate(ExceptionReportUtils.ddMMyyyy2yyyyMMdd(to));
			data.setFacility(getFacilityCode(siteName, session));
			session.setAttribute(ExceptionReportConstants.SUMMARY_DATA, exceptionReportService.getSummary(data));
		}else {
			Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			String acronym = null;
			for(GrantedAuthority auth: auths) {
				acronym = auth.getAuthority();
				acronym = StringUtils.removeStartIgnoreCase(acronym, ExceptionReportConstants.ROLE_PREFIX);
			}
			setHospitals(acronym, session);
		}
		return "summaryPage";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String details(@RequestParam(value="searchCheck", required=false, defaultValue="false") boolean checked, @RequestParam(value="siteName", required=false) String siteName, @RequestParam(value="document", required=false) String document, @RequestParam(value="fromDate", required=false) String from, @RequestParam(value="toDate", required=false) String to, HttpSession session) {
		if(checked) {
			session.setAttribute(ExceptionReportConstants.FROM_DATE, from);
			session.setAttribute(ExceptionReportConstants.TO_DATE, to);
			session.setAttribute(ExceptionReportConstants.DETAIL_FROM_DATE, from);
			session.setAttribute(ExceptionReportConstants.DETAIL_TO_DATE, to);
			session.setAttribute(ExceptionReportConstants.DETAIL_HOSPITAL, siteName);
			String docos = StringUtils.substringBeforeLast(document, ",");
			String[] types = StringUtils.split(docos, ",");
			ExceptionBasicData data = new ExceptionBasicData();
			data.setFromDate(ExceptionReportUtils.ddMMyyyy2yyyyMMdd(from));
			data.setToDate(ExceptionReportUtils.ddMMyyyy2yyyyMMdd(to));
			data.setFacility(getFacilityCode(siteName, session));
			session.setAttribute(ExceptionReportConstants.DETAIL_DATA, exceptionReportService.getExceptionList(data, types));
		}else {
			Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			String acronym = null;
			for(GrantedAuthority auth: auths) {
				acronym = auth.getAuthority();
				acronym = StringUtils.removeStartIgnoreCase(acronym, ExceptionReportConstants.ROLE_PREFIX);
			}
			setHospitals(acronym, session);
		}
		return "detailPage";
	}
	
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
	public String audit(@RequestParam(value="searchCheck", required=false, defaultValue="false") boolean checked, @RequestParam(value="siteName", required=false) String siteName, @RequestParam(value="patientSearch", required=false) String patientSearch, @RequestParam(value="fromDate", required=false) String from, @RequestParam(value="toDate", required=false) String to, HttpSession session) {
		if(checked) {
			session.setAttribute(ExceptionReportConstants.FROM_DATE, from);
			session.setAttribute(ExceptionReportConstants.TO_DATE, to);
			session.setAttribute(ExceptionReportConstants.AUDIT_FROM_DATE, from);
			session.setAttribute(ExceptionReportConstants.AUDIT_TO_DATE, to);
			session.setAttribute(ExceptionReportConstants.AUDIT_HOSPITAL, siteName);
			ExceptionBasicData data = new ExceptionBasicData();
			String fromDate = ExceptionReportUtils.ddMMyyyy2yyyyMMdd(from);
			String toDate = ExceptionReportUtils.ddMMyyyy2yyyyMMdd(to);
			data.setFromDate(fromDate);
			data.setToDate(toDate);
			data.setFacility(getFacilityCode(siteName, session));
			data.setPatientInfo(StringUtils.defaultString(patientSearch));
			session.setAttribute(ExceptionReportConstants.PATIENT_INFO, data.getPatientInfo());
			session.setAttribute(ExceptionReportConstants.AUDIT_DATA, exceptionReportService.getAuditList(data));
		}else {
			Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			String acronym = null;
			for(GrantedAuthority auth: auths) {
				acronym = auth.getAuthority();
				acronym = StringUtils.removeStartIgnoreCase(acronym, ExceptionReportConstants.ROLE_PREFIX);
			}
			setHospitals(acronym, session);
		}
		return "auditPage";
	}
	
	@RequestMapping(value = "/docoUG", method = RequestMethod.GET)
	public String userGuidedocument(HttpServletResponse response) {
		return "docoUGPage";
	}
	
	@RequestMapping(value = "/docoEM", method = RequestMethod.GET)
	public String errorManualdocument(HttpServletResponse response) {
		return "docoEMPage";
	}
	
	@RequestMapping(value = "/docoHS", method = RequestMethod.GET)
	public String hpiiSearchdocument(HttpServletResponse response) {
		return "docoHSPage";
	}
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public RedirectView homePage(Model model) {
        return new RedirectView("dashboard");
    }
 
     
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String contactusPage(Model model) {
        return "loginPage";
    }
    
 // retrieve facility code from saved sites info in session
 	private String getFacilityCode(String siteName, HttpSession session) {
 		String code = "";
 		List<ExceptionSimpleData> sites = (List<ExceptionSimpleData>) session.getAttribute(ExceptionReportConstants.HOSPITALS);
 		for(ExceptionSimpleData data : sites) {
 			if(StringUtils.equalsIgnoreCase(siteName, data.getDisplay())) {
 				code = data.getValue();
 				break;
 			}
 		}
 		return code;
 	}
 	
 	
 	// put hospitals info into session if not exists
 	private HttpSession setHospitals(String authority, HttpSession session) {
 		if(session.getAttribute(ExceptionReportConstants.HOSPITALS)!=null) return session;
 		
 		List<ExceptionSimpleData> sites = null;
 		if((StringUtils.equalsIgnoreCase(authority, ExceptionReportConstants.ADMINISTRATOR))||(StringUtils.equalsIgnoreCase(authority, ExceptionReportConstants.VIEWER))) {
 			sites = exceptionReportService.getSiteAllList();
 		}else {
 			sites = exceptionReportService.getSiteList(authority);
 		}
 		
 		// sort by Display name as an alphabetic order
 		sites = sites.stream().sorted(Comparator.comparing(ExceptionSimpleData::getDisplay)).collect(Collectors.toList());
 		
 		ExceptionSimpleData all = new ExceptionSimpleData();
 		all.setDisplay("All");
 		StringBuffer sb = new StringBuffer();
 		String delimeter = "";
 		for(ExceptionSimpleData site : sites) {
 			sb.append(delimeter);
 			delimeter = ",";
 			sb.append(site.getValue());
 		}
 		all.setValue(sb.toString());
 		
 		
 		
 		if(!(StringUtils.equalsIgnoreCase(authority, ExceptionReportConstants.VIEWER))){// no All option for VIEWER
 			sites.add(0, all);
 		}
 		
 		
 		
 		
 		session.setAttribute(ExceptionReportConstants.HOSPITALS, sites);
 		return session;
 	}
 	
 	
	
}
