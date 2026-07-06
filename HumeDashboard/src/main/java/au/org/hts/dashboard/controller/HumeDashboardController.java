package au.org.hts.dashboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import au.org.hts.dashboard.entity.CommPoint;
import au.org.hts.dashboard.entity.HL7Transaction;
import au.org.hts.dashboard.service.DatabaseService;
import au.org.hts.dashboard.service.HsieService;
import au.org.hts.dashboard.util.HumeDashboardConstants;

@Controller
public class HumeDashboardController {
	
	@Autowired
	private DatabaseService databaseService;
	
	@Autowired
	private HsieService hsieService;

	@RequestMapping(value="/hi", method=RequestMethod.GET)
	@ResponseBody
	public String sayHi() {
		return "Hi Jin";
	}

	
	// Engine health check
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public String showEngines(Map<String, Object> model) {
		// 1. get Engine info
		Map<String, Object> info = hsieService.getDashInfo();
		for(Map.Entry<String, Object> entry : info.entrySet()) {
			model.put(entry.getKey(), entry.getValue());
		}
		// 2. get CommPoint list
		List<CommPoint> points = hsieService.getCommPoints();
		model.put(HumeDashboardConstants.COMMPOINT_LIST, points);
		// 3. go back to dashboard.jsp
		return "dashboard";
	}
	
	// Commpoint section
	@RequestMapping(value="/interface", method=RequestMethod.GET)
	public String showInterfaces(Map<String, Object> model) {
		
		// 1. get CommPoint list
		List<CommPoint> points = hsieService.getCommPoints();
		List<CommPoint> inbound = new ArrayList<CommPoint>();
		List<CommPoint> outbound = new ArrayList<CommPoint>();
		for(CommPoint point : points) {
			String mode = point.getMode();
			if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.COMPOINT_INBOUND, mode)) {
				// add to inbound group
				inbound.add(point);
			}else if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.COMPOINT_OUTBOUND, mode)) {
				// add to outbound group
				outbound.add(point);
			}
		}		
		System.out.println("LEFT >> " + inbound);
		System.out.println("RIGHT >> " + outbound);
		model.put(HumeDashboardConstants.COMMPOINT_INBOUND_LIST, inbound);
		model.put(HumeDashboardConstants.COMMPOINT_OUTBOUND_LIST, outbound);
		// 2. go back to interface.jsp
		return "interface";
	}
	
	// all-in-one
	@RequestMapping(value="/monitor", method=RequestMethod.GET)
	public String showAll(Map<String, Object> model) {
		// 1. get Engine info
		Map<String, Object> info = hsieService.getDashInfo();
		for(Map.Entry<String, Object> entry : info.entrySet()) {
			model.put(entry.getKey(), entry.getValue());
		}
		// 2. get CommPoint list
		//List<CommPoint> points = hsieService.getCommPoints();
		List<CommPoint> points = hsieService.getAllCommPoints();
		
		List<CommPoint> inbound = new ArrayList<CommPoint>();
		List<CommPoint> outbound = new ArrayList<CommPoint>();
		for(CommPoint point : points) {
			String mode = point.getMode();
			if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.COMPOINT_INBOUND, mode)) {
				// add to inbound group
				inbound.add(point);
			}else if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.COMPOINT_OUTBOUND, mode)) {
				// add to outbound group
				outbound.add(point);
			}
		}		
//		System.out.println("LEFT >> " + inbound);
//		System.out.println("RIGHT >> " + outbound);
		model.put(HumeDashboardConstants.COMMPOINT_INBOUND_LIST, inbound);
		model.put(HumeDashboardConstants.COMMPOINT_OUTBOUND_LIST, outbound);
		// 3. go back to monitor.jsp
		return "monitor";
	}
	
	// Logs section
	@GetMapping("/getLogSection/{service}")
	@ResponseBody
	public List<HL7Transaction> getLog4Service(@PathVariable String service) {
		List<HL7Transaction> dtos = new ArrayList();
		dtos = databaseService.getTransactionLog(service);	
		return dtos;
	}
	
	// Log detail
	@GetMapping("/logDetail/{id}")
	@ResponseBody
	public String getLogDetail(@PathVariable Long id) {
		String msg = "";
		msg = databaseService.getLogDetail(id);	
		return msg;
	}
	
	// Show sign in page
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String signIn(Map<String, Object> model) {
		return "login";
	}
	
}
