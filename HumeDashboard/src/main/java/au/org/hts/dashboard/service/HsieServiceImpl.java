package au.org.hts.dashboard.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.org.hts.dashboard.dao.RhapsodyApiConnector;
import au.org.hts.dashboard.entity.CommPoint;
import au.org.hts.dashboard.entity.Engine;
import au.org.hts.dashboard.entity.Organisation;
import au.org.hts.dashboard.util.HumeDashboardConstants;
import au.org.hts.dashboard.util.HumeDashboardUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



@Service
public class HsieServiceImpl implements HsieService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RhapsodyApiConnector rhapsodyApiConnector;
	
	@Value("${max.cpu.threshold}")
	private String cpuThreshold;
	
	@Value("${max.disk.threshold}")
	private String diskThreshold;
	
	@Value("${max.memory.threshold}")
	private String memoryThreshold;
	
	@Value("${rest.server.p1}")
	private String p1Server;
		
	
	@Transactional
	@Override
	public List<CommPoint> getCommPoints(String portfolio, String acronym, String project) throws ParseException {
		String engine= getEngineAddress(portfolio);
		// set token for rest of transactions
		rhapsodyApiConnector.setToken(engine);
		// bring up CommPoints for particular agency
		List<CommPoint> points = null;
		if(StringUtils.isNotBlank(project)) {
			points = getCommPointListByOrganisationAndProject(engine, acronym, project);
		}else {
			points = getCommPointListByOrganisation(engine, acronym);
		}
		return points;
	}

	
	@Override
	public List<CommPoint> getCommPoints(){
		// set token for rest of transactions
		rhapsodyApiConnector.setToken();
		// bring up CommPoints
		List<CommPoint> components = new ArrayList<CommPoint>();
		String json = rhapsodyApiConnector.getComponentsStatus();
		JSONParser jParser = new JSONParser();
		JSONObject jObj = null;
		try {
			jObj = (JSONObject) ((JSONObject) jParser.parse(json)).get(HumeDashboardConstants.COMPONENT_DATA);
			JSONArray parentArray = (JSONArray) jObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
			for(int i=0; i<parentArray.size(); i++) {
				JSONObject htsObj = (JSONObject) parentArray.get(i);
				String htsName = (String) htsObj.get(HumeDashboardConstants.COMPONENT_NAME); // HTS, Connection Test Route
				if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.HTS, htsName)) {
					JSONArray htsArray = (JSONArray) htsObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
					for(int j=0; j<htsArray.size(); j++) { 
						JSONObject htsRouteObj = (JSONObject) htsArray.get(j); // Input, Processing, Output, Common
						log.trace((String) htsRouteObj.get(HumeDashboardConstants.COMPONENT_NAME)); 
						//JSONArray routerArray = (JSONArray) htsRouteObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
						
						//for(int k=0; k<routerArray.size(); k++) {
							//JSONObject agencyObj = (JSONObject) routerArray.get(k);// ALF, EHS, GHA.......
							//String agencyObjName = (String) agencyObj.get(HumeDashboardConstants.COMPONENT_NAME);
							//log.trace(agencyObjName); 
							
							//if(StringUtils.equalsIgnoreCase(agencyName, agencyObjName)) {
								
								JSONArray agencyArray = (JSONArray) htsRouteObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
								
								for(int l=0; l<agencyArray.size(); l++) {
									JSONObject agencyFolders = (JSONObject) agencyArray.get(l);// ALF From AIE, ALF From AIE QRY, ALF From IPM.......
									String agencyFolderName = (String) agencyFolders.get(HumeDashboardConstants.COMPONENT_NAME);
									log.trace(agencyFolderName); 
									JSONArray commArray = (JSONArray) agencyFolders.get(HumeDashboardConstants.COMPONENT_CHILD_COMPONENT);
									
									for(int m=0; m<commArray.size(); m++) {
										JSONObject commpoint = (JSONObject) commArray.get(m);// CP.dr_ALF From AIE_O, CP.tcp_ALF From AIE_IO.....
										String commName = (String) commpoint.get(HumeDashboardConstants.COMPONENT_NAME);
										
										String commState = (String) commpoint.get(HumeDashboardConstants.COMPONENT_STATE);
										log.trace(commName + "\t" + commState); 
										
										// get dequeue time if state == 'RUNNING' & commpoint starts with 'CP.tcp' or 'CP.http'
										if(HumeDashboardConstants.STATE_RUNNING.equals(commState)  && (commName.contains(HumeDashboardConstants.COMPOINT_TCP_PREFIX)||commName.contains(HumeDashboardConstants.COMPOINT_HTTP_PREFIX))) {
											Long commId = (Long) commpoint.get(HumeDashboardConstants.COMPONENT_ID);
											CommPoint point = restApiCallCommPoint(commId.toString());
											components.add(point);
										}
									}
								}
							//}// end of agency check
						//}
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return components;	
	
	}
	
	@Override
	public List<CommPoint> getAllCommPoints(){
		// set token for rest of transactions
		rhapsodyApiConnector.setToken();
		// bring up CommPoints
		List<CommPoint> components = new ArrayList<CommPoint>();
		String json = rhapsodyApiConnector.getComponentsStatus();
		JSONParser jParser = new JSONParser();
		JSONObject jObj = null;
		try {
			jObj = (JSONObject) ((JSONObject) jParser.parse(json)).get(HumeDashboardConstants.COMPONENT_DATA);
			JSONArray parentArray = (JSONArray) jObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
			for(int i=0; i<parentArray.size(); i++) {
				JSONObject htsObj = (JSONObject) parentArray.get(i);
				String htsName = (String) htsObj.get(HumeDashboardConstants.COMPONENT_NAME); // HTS, Connection Test Route
				if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.HTS, htsName)) {
					JSONArray htsArray = (JSONArray) htsObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
					for(int j=0; j<htsArray.size(); j++) { 
						JSONObject htsRouteObj = (JSONObject) htsArray.get(j); // Input, Processing, Output, Common
						log.trace((String) htsRouteObj.get(HumeDashboardConstants.COMPONENT_NAME)); 
								
						JSONArray agencyArray = (JSONArray) htsRouteObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
						
						for(int l=0; l<agencyArray.size(); l++) {
							JSONObject agencyFolders = (JSONObject) agencyArray.get(l);// ALF From AIE, ALF From AIE QRY, ALF From IPM.......
							collectCommPointsFromFolder(agencyFolders, components);
						}
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return components;	
	
	}

	private void collectCommPointsFromFolder(JSONObject folder, List<CommPoint> components) throws ParseException {
		String folderName = (String) folder.get(HumeDashboardConstants.COMPONENT_NAME);
		log.trace(folderName);

		JSONArray commArray = (JSONArray) folder.get(HumeDashboardConstants.COMPONENT_CHILD_COMPONENT);
		if (commArray != null) {
			for (int m = 0; m < commArray.size(); m++) {
				JSONObject commpoint = (JSONObject) commArray.get(m);
				String commName = (String) commpoint.get(HumeDashboardConstants.COMPONENT_NAME);
				if (commName.contains(HumeDashboardConstants.COMPOINT_TCP_PREFIX)
						|| commName.contains(HumeDashboardConstants.COMPOINT_HTTP_PREFIX)) {
					Long commId = (Long) commpoint.get(HumeDashboardConstants.COMPONENT_ID);
					CommPoint point = restApiCallCommPoint(commId.toString());
					components.add(point);
				}
			}
		}

		JSONArray childFolders = (JSONArray) folder.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
		if (childFolders != null) {
			for (int n = 0; n < childFolders.size(); n++) {
				JSONObject childFolder = (JSONObject) childFolders.get(n);
				collectCommPointsFromFolder(childFolder, components);
			}
		}
	}


	
	
	@Override
	@Transactional
	public List<Organisation> getOrganisations(String portfolio) throws ParseException {
		
		String engine= getEngineAddress(portfolio);//"https://p01902.prod.services:5141";
		// set token for rest of transactions
		rhapsodyApiConnector.setToken(engine);
		
		// initiate organisations
		List<Organisation> orgs =null;// dashboardConfigDao.getOrganisationList(portfolio);
		
		// bring up CommPoints for particular agency
		List<CommPoint> points = null;
		if(portfolio==HumeDashboardConstants.PORTFOLIO_CMS) {
			points = getCommPointListByProject(engine, HumeDashboardConstants.PROJECT_CMS);
		}else {
			points = getCommPointList(engine);
		}
		
				
		log.trace("points >> " + points);

		// associate compoints to specific group
		for(CommPoint point : points) {
			String pointName = point.getName();
			Organisation agency = findAgency(orgs, pointName);
			if(agency != null) {
				agency.getCompoints().add(point);
			}
		}
		
		return orgs;
	}
	
	
	// find the agency in order to group Compoints
	private Organisation findAgency(List<Organisation> agencies, String name) {
		Organisation agency = null;
		for(Organisation vo : agencies) {
			if(StringUtils.contains(name, vo.getAcronym())) {
				agency = vo;
				break;
			}
		}
		return agency;
	}
			
	@Override
	public Map<String, Object> getDashInfo(){
		Hashtable<String, Object> infos = new Hashtable<String, Object>();
		// 1. set Token
		rhapsodyApiConnector.setToken();
		// 2. get basic info
		String basicInfo = rhapsodyApiConnector.getBasicInfo();
		Engine engineVO = null;
		try {
			engineVO = engineBasicInfo(basicInfo);
			// 3. get disk info
			String diskInfo = rhapsodyApiConnector.getDiskspace();
			engineVO = engineAddDiskInfo(engineVO, diskInfo);
			// 4. get memory info
			String memoryInfo = rhapsodyApiConnector.getSimpleMemoryUsage();
			engineVO = engineAddMemoryInfo(engineVO, memoryInfo);
			// 5. get total message count
			String totalMsg = StringUtils.defaultString(rhapsodyApiConnector.getTotalMessageCount(), HumeDashboardConstants.NOTHING);
			// 6. get CPU usage
			String cpu = StringUtils.defaultString(rhapsodyApiConnector.getCPUUsage(), HumeDashboardConstants.ZERO);
			// 7. get memo last 30 mins
			String memo = rhapsodyApiConnector.getMemoryUsage();
			// 8. wrap infos into Map
			infos.put(HumeDashboardConstants.ENGINE_INFO, engineVO);
			infos.put(HumeDashboardConstants.TOTAL_MESSAGE_COUNT, totalMsg); 
			infos.put(HumeDashboardConstants.CPU_INFO, cpu);
			infos.put(HumeDashboardConstants.MEMORY_INFO, memo);
			// 8. high level health check
			if(!checkEngineHealth(cpu, engineVO)) {
				infos.put(HumeDashboardConstants.ENGINE_HEALTH, "bad");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 9. return Map
		return infos;
	}



	@Override
	@Transactional
	public Map<String, Object> getDashInfo(String engine) throws ParseException {
		
		Hashtable<String, Object> infos = new Hashtable<String, Object>();
		rhapsodyApiConnector.setToken(engine);
		// retrieves engine version, name and uptime duration
		String enInfo1 = rhapsodyApiConnector.getBasicInfo(engine);
		Engine engineVO = engineBasicInfo(enInfo1);
		// add Disk info into EngineVO
		String enInfo2 = rhapsodyApiConnector.getDiskspace(engine);
		engineVO = engineAddDiskInfo(engineVO, enInfo2);
		// add simple Memory info into EngineVO
		String enInfo3 = rhapsodyApiConnector.getSimpleMemoryUsage(engine);
		engineVO =engineAddMemoryInfo(engineVO, enInfo3);
		
		// retrieves toatl message count
		String totalMsg = StringUtils.defaultString(rhapsodyApiConnector.getTotalMessageCount(engine), HumeDashboardConstants.NOTHING);
		// retrieves CPU usage
		String cpu = StringUtils.defaultString(rhapsodyApiConnector.getCPUUsage(engine), HumeDashboardConstants.ZERO);
		
		
		// retrieves memory usage for last 30 mins
		String memo = rhapsodyApiConnector.getMemoryUsage(engine);
		infos.put(HumeDashboardConstants.ENGINE_INFO, engineVO);
		infos.put(HumeDashboardConstants.TOTAL_MESSAGE_COUNT, totalMsg); 
		infos.put(HumeDashboardConstants.CPU_INFO, cpu);
		
		
		// Just for simple test
		if(!checkEngineHealth(cpu, engineVO)) {
			infos.put(HumeDashboardConstants.ENGINE_HEALTH, "bad");
		}
		
		log.debug(memo);
		infos.put(HumeDashboardConstants.MEMORY_INFO, memo);
		
		return infos;
	}
	
	private Engine engineBasicInfo(String data) throws ParseException {
		// engine -{"data":{"version":"6.5.0","name":"HSIE-UAT-1","uptime":"P0Y0M8DT16H26M7.504S"},"error":null}
		Engine engine = new Engine();
		if(data == null) {
			return engine;
		}
		JSONParser jParser = new JSONParser();
		JSONObject dataObject = (JSONObject) ((JSONObject) jParser.parse(data)).get(HumeDashboardConstants.COMPONENT_DATA);
		String version = (dataObject.get(HumeDashboardConstants.ENGINE_VERSION) != null)
				? dataObject.get(HumeDashboardConstants.ENGINE_VERSION).toString()
				: HumeDashboardConstants.NOTHING;
		String name = (dataObject.get(HumeDashboardConstants.ENGINE_NAME) != null)
				? dataObject.get(HumeDashboardConstants.ENGINE_NAME).toString()
				: HumeDashboardConstants.NOTHING;
		String uptime = (dataObject.get(HumeDashboardConstants.ENGINE_UPTIME) != null)
				? dataObject.get(HumeDashboardConstants.ENGINE_UPTIME).toString()
				: HumeDashboardConstants.NOTHING;
		engine.setVersion(version);
		engine.setName(name);
		engine.setUptime(HumeDashboardUtils.convertISO8601(uptime));
		
		
		return engine;
	}
	
	private Engine engineAddDiskInfo(Engine vo, String data) throws ParseException {
		Engine engine = vo;
		if(data == null) {
			return engine;
		}
		
		JSONParser jParser = new JSONParser();
		JSONObject dataObject = (JSONObject) ((JSONObject) jParser.parse(data)).get(HumeDashboardConstants.COMPONENT_DATA);
		
		String available = (dataObject.get(HumeDashboardConstants.ENGINE_AVAILABLE_DISK) != null)
				? String.valueOf(dataObject.get(HumeDashboardConstants.ENGINE_AVAILABLE_DISK))
				: HumeDashboardConstants.ZERO;
		String total = (dataObject.get(HumeDashboardConstants.ENGINE_TOTAL_DISK) != null)
				? String.valueOf(dataObject.get(HumeDashboardConstants.ENGINE_TOTAL_DISK))
				: HumeDashboardConstants.ZERO;
		engine.setAvailableDisk(available);
		engine.setTotalDisk(total);
		
		return engine;
	}
	

	private Engine engineAddMemoryInfo(Engine vo, String data) throws ParseException {
		Engine engine = vo;
		if(data == null) {
			return engine;
		}
	
		JSONParser jParser = new JSONParser();
		JSONObject dataObject = (JSONObject) ((JSONObject) jParser.parse(data)).get(HumeDashboardConstants.COMPONENT_DATA);
		
		
		String available = (dataObject.get(HumeDashboardConstants.ENGINE_AVAILABLE_MEMORY) != null)
				? String.valueOf(dataObject.get(HumeDashboardConstants.ENGINE_AVAILABLE_MEMORY))
				: HumeDashboardConstants.NOTHING;
		String total = (dataObject.get(HumeDashboardConstants.ENGINE_TOTAL_MEMORY) != null)
				? String.valueOf(dataObject.get(HumeDashboardConstants.ENGINE_TOTAL_MEMORY))
				: HumeDashboardConstants.NOTHING;
		engine.setAvailableMemory(available);
		engine.setTotalMemory(total);
		
		return engine;
	}
	
	// check if engine is stable or not
	private boolean checkEngineHealth(String cpu, Engine engine) {
		boolean isHealthy = true;
		try {
			// check CPU
			if(Double.parseDouble(cpu) > Double.parseDouble(cpuThreshold)) {
				isHealthy = false;
			}
			// check Disk
			double diskRate = Double.parseDouble(engine.getAvailableDisk())/Double.parseDouble(engine.getTotalDisk());
			if(diskRate > Double.parseDouble(diskThreshold)) {
				isHealthy = false;
			}
			// check Memory
			double memoryRate = Double.parseDouble(engine.getAvailableMemory())/Double.parseDouble(engine.getTotalMemory());
			if(memoryRate > Double.parseDouble(memoryThreshold)) {
				isHealthy = false;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isHealthy;
	}
	
	// retrieve api web address
	private String getEngineAddress(String portfolio) {
		String address = "";
		switch(portfolio)
		{
			case HumeDashboardConstants.PORTFOLIO_PCMS :
				address = p1Server;
				break;
			case HumeDashboardConstants.PORTFOLIO_CS :
//				address = p4Server;
				break;
				
			default :	
			//case HumeDashboardConstants.PORTFOLIO_CMS :
//				address = p2Server;
		}
		
		return address;
	}
	
	
	
	// retreive CommPoints list for p1 & p3
	private List<CommPoint> getCommPointList(String engine) throws ParseException {
		
		List<CommPoint> components = new ArrayList<CommPoint>();
		String json = rhapsodyApiConnector.getComponentsStatus(engine);
		log.trace(json);
		JSONParser jParser = new JSONParser();
		JSONObject jObj = (JSONObject) ((JSONObject) jParser.parse(json)).get(HumeDashboardConstants.COMPONENT_DATA);
		JSONArray parentArray = (JSONArray) jObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
		for(int i=0; i<parentArray.size(); i++) {
			JSONObject htsObj = (JSONObject) parentArray.get(i);
			String htsName = (String) htsObj.get(HumeDashboardConstants.COMPONENT_NAME); // HTS, Connection Test Route
			if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.HTS, htsName)) {
				JSONArray htsArray = (JSONArray) htsObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
				for(int j=0; j<htsArray.size(); j++) { 
					JSONObject htsRouteObj = (JSONObject) htsArray.get(j); // Input, Processing, Output, Common
					log.trace((String) htsRouteObj.get(HumeDashboardConstants.COMPONENT_NAME)); 
					JSONArray routerArray = (JSONArray) htsRouteObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
					
					for(int k=0; k<routerArray.size(); k++) {
						JSONObject agencyObj = (JSONObject) routerArray.get(k);// ALF, EHS, GHA.......
						String agencyObjName = (String) agencyObj.get(HumeDashboardConstants.COMPONENT_NAME);
						log.trace(agencyObjName); 
							
						JSONArray agencyArray = (JSONArray) agencyObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
						
						for(int l=0; l<agencyArray.size(); l++) {
							JSONObject agencyFolders = (JSONObject) agencyArray.get(l);// ALF From AIE, ALF From AIE QRY, ALF From IPM.......
							String agencyFolderName = (String) agencyFolders.get(HumeDashboardConstants.COMPONENT_NAME);
							log.trace(agencyFolderName); 
							JSONArray commArray = (JSONArray) agencyFolders.get(HumeDashboardConstants.COMPONENT_CHILD_COMPONENT);
							
							for(int m=0; m<commArray.size(); m++) {
								JSONObject commpoint = (JSONObject) commArray.get(m);// CP.dr_ALF From AIE_O, CP.tcp_ALF From AIE_IO.....
								String commName = (String) commpoint.get(HumeDashboardConstants.COMPONENT_NAME);
								
								String commState = (String) commpoint.get(HumeDashboardConstants.COMPONENT_STATE);
								log.trace(commName + "\t" + commState); 
								
								// get dequeue time if state == 'RUNNING' & commpoint starts with 'CP.tcp' or 'CP.http'
								if(HumeDashboardConstants.STATE_RUNNING.equals(commState)  && (commName.contains(HumeDashboardConstants.COMPOINT_TCP_PREFIX)||commName.contains(HumeDashboardConstants.COMPOINT_HTTP_PREFIX))) {
									Long commId = (Long) commpoint.get(HumeDashboardConstants.COMPONENT_ID);
									CommPoint point = restApiCallCommPoint(engine, commId.toString());
									components.add(point);
								}
							}
						}
					}
				}
			}
		}
		return components;
	}
	
	
	
	// retreive CommPoints list for p2 projects
	private List<CommPoint> getCommPointListByProject(String engine, String project) throws ParseException {
		
		List<CommPoint> components = new ArrayList<CommPoint>();
		String json = rhapsodyApiConnector.getComponentsStatus(engine);
		log.debug("The whole JSON ==> " + json);
		JSONParser jParser = new JSONParser();
		JSONObject jObj = (JSONObject) ((JSONObject) jParser.parse(json)).get(HumeDashboardConstants.COMPONENT_DATA);
		JSONArray parentArray = (JSONArray) jObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
		for(int i=0; i<parentArray.size(); i++) {
			JSONObject htsObj = (JSONObject) parentArray.get(i);
			String htsName = (String) htsObj.get(HumeDashboardConstants.COMPONENT_NAME); // HTS, Connection Test Route
			if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.HTS, htsName)) {
				JSONArray htsArray = (JSONArray) htsObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
				
				for(int j=0; j<htsArray.size(); j++) { 
					JSONObject projectObj = (JSONObject) htsArray.get(j); // RHEMS, TRAK, FBI
					String projectName = (String) projectObj.get(HumeDashboardConstants.COMPONENT_NAME);
					///////////////////////////////////////////////////////////////////////////////////////////
					//		Check if project name is right
					///////////////////////////////////////////////////////////////////////////////////////////
					if(StringUtils.equalsIgnoreCase(project, projectName)) { 
					JSONArray projectArray = (JSONArray) projectObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);		
					for(int k=0; k<projectArray.size(); k++) { 
						JSONObject htsRouteObj = (JSONObject) projectArray.get(k); // Input, Processing, Output, Common
						log.debug("Input...." + (String) htsRouteObj.get(HumeDashboardConstants.COMPONENT_NAME)); 
						JSONArray routerArray = (JSONArray) htsRouteObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
						
						for(int l=0; l<routerArray.size(); l++) {
							JSONObject agencyObj = (JSONObject) routerArray.get(l);// ALF, EHS, GHA.......
							String agencyObjName = (String) agencyObj.get(HumeDashboardConstants.COMPONENT_NAME);
							log.debug("ALF...." + agencyObjName); 
								
							JSONArray agencyArray = (JSONArray) agencyObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
							
							for(int m=0; m<agencyArray.size(); m++) {
								JSONObject agencyFolders = (JSONObject) agencyArray.get(m);// ALF From AIE, ALF From AIE QRY, ALF From IPM.......
								String agencyFolderName = (String) agencyFolders.get(HumeDashboardConstants.COMPONENT_NAME);
								log.debug("ALF From AIE...." + agencyFolderName); 
								JSONArray commArray = (JSONArray) agencyFolders.get(HumeDashboardConstants.COMPONENT_CHILD_COMPONENT);
								
								for(int o=0; o<commArray.size(); o++) {
									JSONObject commpoint = (JSONObject) commArray.get(o);// CP.dr_ALF From AIE_O, CP.tcp_ALF From AIE_IO.....
									String commName = (String) commpoint.get(HumeDashboardConstants.COMPONENT_NAME);
									
									String commState = (String) commpoint.get(HumeDashboardConstants.COMPONENT_STATE);
									log.debug(commpoint.get("folderPath")+ "\t" + commName + "\t" + commState); 
									
									// get dequeue time if state == 'RUNNING' & commpoint starts with 'CP.tcp' or 'CP.http'
									if(HumeDashboardConstants.STATE_RUNNING.equals(commState)  && (commName.contains(HumeDashboardConstants.COMPOINT_TCP_PREFIX)||commName.contains(HumeDashboardConstants.COMPOINT_HTTP_PREFIX))) {
										Long commId = (Long) commpoint.get(HumeDashboardConstants.COMPONENT_ID);
										CommPoint point = restApiCallCommPoint(engine, commId.toString());
										components.add(point);
										}
									}
								}
							}// end of agency level
						}
					}// end of project level
				}
			}
		}
		log.debug("list ==> " + components);
		return components;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	// retreive CommPoints list
	private List<CommPoint> getCommPointListByOrganisation(String engine, String agencyName) throws ParseException {
		
		List<CommPoint> components = new ArrayList<CommPoint>();
		String json = rhapsodyApiConnector.getComponentsStatus(engine);
		JSONParser jParser = new JSONParser();
		JSONObject jObj = (JSONObject) ((JSONObject) jParser.parse(json)).get(HumeDashboardConstants.COMPONENT_DATA);
		JSONArray parentArray = (JSONArray) jObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
		for(int i=0; i<parentArray.size(); i++) {
			JSONObject htsObj = (JSONObject) parentArray.get(i);
			String htsName = (String) htsObj.get(HumeDashboardConstants.COMPONENT_NAME); // HTS, Connection Test Route
			if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.HTS, htsName)) {
				JSONArray htsArray = (JSONArray) htsObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
				for(int j=0; j<htsArray.size(); j++) { 
					JSONObject htsRouteObj = (JSONObject) htsArray.get(j); // Input, Processing, Output, Common
					log.trace((String) htsRouteObj.get(HumeDashboardConstants.COMPONENT_NAME)); 
					JSONArray routerArray = (JSONArray) htsRouteObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
					
					for(int k=0; k<routerArray.size(); k++) {
						JSONObject agencyObj = (JSONObject) routerArray.get(k);// ALF, EHS, GHA.......
						String agencyObjName = (String) agencyObj.get(HumeDashboardConstants.COMPONENT_NAME);
						log.trace(agencyObjName); 
						
						if(StringUtils.equalsIgnoreCase(agencyName, agencyObjName)) {
							
							JSONArray agencyArray = (JSONArray) agencyObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
							
							for(int l=0; l<agencyArray.size(); l++) {
								JSONObject agencyFolders = (JSONObject) agencyArray.get(l);// ALF From AIE, ALF From AIE QRY, ALF From IPM.......
								String agencyFolderName = (String) agencyFolders.get(HumeDashboardConstants.COMPONENT_NAME);
								log.trace(agencyFolderName); 
								JSONArray commArray = (JSONArray) agencyFolders.get(HumeDashboardConstants.COMPONENT_CHILD_COMPONENT);
								
								for(int m=0; m<commArray.size(); m++) {
									JSONObject commpoint = (JSONObject) commArray.get(m);// CP.dr_ALF From AIE_O, CP.tcp_ALF From AIE_IO.....
									String commName = (String) commpoint.get(HumeDashboardConstants.COMPONENT_NAME);
									
									String commState = (String) commpoint.get(HumeDashboardConstants.COMPONENT_STATE);
									log.trace(commName + "\t" + commState); 
									
									// get dequeue time if state == 'RUNNING' & commpoint starts with 'CP.tcp' or 'CP.http'
									if(HumeDashboardConstants.STATE_RUNNING.equals(commState)  && (commName.contains(HumeDashboardConstants.COMPOINT_TCP_PREFIX)||commName.contains(HumeDashboardConstants.COMPOINT_HTTP_PREFIX))) {
										Long commId = (Long) commpoint.get(HumeDashboardConstants.COMPONENT_ID);
										CommPoint point = restApiCallCommPoint(engine, commId.toString());
										components.add(point);
									}
								}
							}
						}// end of agency check
					}
				}
			}
		}
		return components;
	}
	
	
	
	// retreive CommPoints list
	private List<CommPoint> getCommPointListByOrganisationAndProject(String engine, String agency, String project) throws ParseException {
		
		List<CommPoint> components = new ArrayList<CommPoint>();
		String json = rhapsodyApiConnector.getComponentsStatus(engine);
		log.debug("The whole JSON ==> " + json);
		JSONParser jParser = new JSONParser();
		JSONObject jObj = (JSONObject) ((JSONObject) jParser.parse(json)).get(HumeDashboardConstants.COMPONENT_DATA);
		JSONArray parentArray = (JSONArray) jObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
		for(int i=0; i<parentArray.size(); i++) {
			JSONObject htsObj = (JSONObject) parentArray.get(i);
			String htsName = (String) htsObj.get(HumeDashboardConstants.COMPONENT_NAME); // HTS, Connection Test Route
			if(StringUtils.equalsIgnoreCase(HumeDashboardConstants.HTS, htsName)) {
				JSONArray htsArray = (JSONArray) htsObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
				
				for(int j=0; j<htsArray.size(); j++) { 
					JSONObject projectObj = (JSONObject) htsArray.get(j); // RHEMS, TRAK, FBI
					String projectName = (String) projectObj.get(HumeDashboardConstants.COMPONENT_NAME);
					///////////////////////////////////////////////////////////////////////////////////////////
					//		Check if project name is right
					///////////////////////////////////////////////////////////////////////////////////////////
					if(StringUtils.equalsIgnoreCase(project, projectName)) { 
					JSONArray projectArray = (JSONArray) projectObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);		
					for(int k=0; k<projectArray.size(); k++) { 
						JSONObject htsRouteObj = (JSONObject) projectArray.get(k); // Input, Processing, Output, Common
						log.debug("Input...." + (String) htsRouteObj.get(HumeDashboardConstants.COMPONENT_NAME)); 
						JSONArray routerArray = (JSONArray) htsRouteObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
						
						for(int l=0; l<routerArray.size(); l++) {
							JSONObject agencyObj = (JSONObject) routerArray.get(l);// ALF, EHS, GHA.......
							String agencyObjName = (String) agencyObj.get(HumeDashboardConstants.COMPONENT_NAME);
							log.debug("ALF...." + agencyObjName); 
							///////////////////////////////////////////////////////////////////////////////////////////
							//		Check if agency is right
							///////////////////////////////////////////////////////////////////////////////////////////
							if(StringUtils.equalsIgnoreCase(agency, agencyObjName)) {
							
								JSONArray agencyArray = (JSONArray) agencyObj.get(HumeDashboardConstants.COMPONENT_CHILD_FOLDERS);
								
								for(int m=0; m<agencyArray.size(); m++) {
									JSONObject agencyFolders = (JSONObject) agencyArray.get(m);// ALF From AIE, ALF From AIE QRY, ALF From IPM.......
									String agencyFolderName = (String) agencyFolders.get(HumeDashboardConstants.COMPONENT_NAME);
									log.debug("ALF From AIE...." + agencyFolderName); 
									JSONArray commArray = (JSONArray) agencyFolders.get(HumeDashboardConstants.COMPONENT_CHILD_COMPONENT);
									
									for(int o=0; o<commArray.size(); o++) {
										JSONObject commpoint = (JSONObject) commArray.get(o);// CP.dr_ALF From AIE_O, CP.tcp_ALF From AIE_IO.....
										String commName = (String) commpoint.get(HumeDashboardConstants.COMPONENT_NAME);
										
										String commState = (String) commpoint.get(HumeDashboardConstants.COMPONENT_STATE);
										log.debug(commpoint.get("folderPath")+ "\t" + commName + "\t" + commState); 
										
										// get dequeue time if state == 'RUNNING' & commpoint starts with 'CP.tcp' or 'CP.http'
										if(HumeDashboardConstants.STATE_RUNNING.equals(commState)  && (commName.contains(HumeDashboardConstants.COMPOINT_TCP_PREFIX)||commName.contains(HumeDashboardConstants.COMPOINT_HTTP_PREFIX))) {
											Long commId = (Long) commpoint.get(HumeDashboardConstants.COMPONENT_ID);
											CommPoint point = restApiCallCommPoint(engine, commId.toString());
											components.add(point);
											}
										}
									}
								}
							}// end of agency level
						}
					}// end of project level
				}
			}
		}
		log.debug("list ==> " + components);
		return components;
		
		
		
		
	}

	
	
	
	
	// retrieve particular CommPoint info based on id
	private CommPoint restApiCallCommPoint(String id) throws ParseException {
		
		String commInfo = rhapsodyApiConnector.getCommpoint(id);
		
		JSONParser jParser = new JSONParser();
		JSONObject comObj = (JSONObject) ((JSONObject) jParser.parse(commInfo)).get(HumeDashboardConstants.COMPONENT_DATA);
		
		// name
		String name = (comObj.get(HumeDashboardConstants.COMPONENT_NAME)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.COMPONENT_NAME)) : HumeDashboardConstants.NOTHING ;
		// mode
		String mode = (comObj.get(HumeDashboardConstants.COMPOINT_MODE)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.COMPOINT_MODE)) : HumeDashboardConstants.NOTHING ;
		// state
		String state = (comObj.get(HumeDashboardConstants.COMPONENT_STATE)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.COMPONENT_STATE)) : HumeDashboardConstants.NOTHING ;
		// path
		String path = (comObj.get(HumeDashboardConstants.COMPOINT_FOLDER_PATH)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.COMPOINT_FOLDER_PATH)) : HumeDashboardConstants.NOTHING ;
		// inputIdleTime
		String intime = (comObj.get(HumeDashboardConstants.INBOUND_IDLE_TIME)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.INBOUND_IDLE_TIME)) : HumeDashboardConstants.NOTHING ;
		// outputIdleTime
		String outtime = (comObj.get(HumeDashboardConstants.OUTBOUND_IDLE_TIME)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.OUTBOUND_IDLE_TIME)) : HumeDashboardConstants.NOTHING ;
		// uptime
		String uptime = (comObj.get(HumeDashboardConstants.UPTIME)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.UPTIME)) : HumeDashboardConstants.NOTHING ;
		// inQueueSize
		Object inbound = comObj.get(HumeDashboardConstants.INBOUND_QUEUE_SIZE);
		int inboundQueue = NumberUtils.toInt(String.valueOf(inbound), 0);
		// outQueueSize
		Object outbound = comObj.get(HumeDashboardConstants.OUTBOUND_QUEUE_SIZE);
		int outboundQueue = NumberUtils.toInt(String.valueOf(outbound), 0);
		// connectionCount
		Object conCnt = comObj.get(HumeDashboardConstants.CONNECTION_COUNT);
		int connectionCnt = NumberUtils.toInt(String.valueOf(conCnt), 0);
		// receivedCount
		Object rxCnt = comObj.get(HumeDashboardConstants.COMPOINT_RECEIVE_COUNT);
		int receivedCnt = NumberUtils.toInt(String.valueOf(rxCnt), 0);
		// sentCount
		Object stCnt = comObj.get(HumeDashboardConstants.COMPOINT_SENT_COUNT);
		int sentCnt = NumberUtils.toInt(String.valueOf(stCnt), 0);
		
		CommPoint commpoint = new CommPoint();
		commpoint.setName(name);
		commpoint.setId(id);
		commpoint.setState(state);
		commpoint.setMode(mode);
		commpoint.setFolderPath(path);
		commpoint.setInputIdleTime(HumeDashboardUtils.convertDuraton(intime));
		commpoint.setOutputIdleTime(HumeDashboardUtils.convertDuraton(outtime));
		commpoint.setUptime(HumeDashboardUtils.convertDuraton(uptime));
		commpoint.setInQueueSize(inboundQueue);
		commpoint.setOutQueueSize(outboundQueue);
		commpoint.setConnectionCount(connectionCnt);
		commpoint.setReceivedCount(receivedCnt);
		commpoint.setSentCount(sentCnt);
		

		return commpoint;
	}
	
	// retrieve particular CommPoint info based on id
	private CommPoint restApiCallCommPoint(String engine, String id) throws ParseException {
		
		String commInfo = rhapsodyApiConnector.getCommpoint(engine, id);
		
		JSONParser jParser = new JSONParser();
		JSONObject comObj = (JSONObject) ((JSONObject) jParser.parse(commInfo)).get(HumeDashboardConstants.COMPONENT_DATA);
		
		// name
		String name = (comObj.get(HumeDashboardConstants.COMPONENT_NAME)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.COMPONENT_NAME)) : HumeDashboardConstants.NOTHING ;
		// mode
		String mode = (comObj.get(HumeDashboardConstants.COMPOINT_MODE)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.COMPOINT_MODE)) : HumeDashboardConstants.NOTHING ;
		// state
		String state = (comObj.get(HumeDashboardConstants.COMPONENT_STATE)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.COMPONENT_STATE)) : HumeDashboardConstants.NOTHING ;
		// path
		String path = (comObj.get(HumeDashboardConstants.COMPOINT_FOLDER_PATH)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.COMPOINT_FOLDER_PATH)) : HumeDashboardConstants.NOTHING ;
		// inputIdleTime
		String intime = (comObj.get(HumeDashboardConstants.INBOUND_IDLE_TIME)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.INBOUND_IDLE_TIME)) : HumeDashboardConstants.NOTHING ;
		// outputIdleTime
		String outtime = (comObj.get(HumeDashboardConstants.OUTBOUND_IDLE_TIME)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.OUTBOUND_IDLE_TIME)) : HumeDashboardConstants.NOTHING ;
		// uptime
		String uptime = (comObj.get(HumeDashboardConstants.UPTIME)!=null) ? String.valueOf(comObj.get(HumeDashboardConstants.UPTIME)) : HumeDashboardConstants.NOTHING ;
		// inQueueSize
		Object inbound = comObj.get(HumeDashboardConstants.INBOUND_QUEUE_SIZE);
		int inboundQueue = NumberUtils.toInt(String.valueOf(inbound), 0);
		// outQueueSize
		Object outbound = comObj.get(HumeDashboardConstants.OUTBOUND_QUEUE_SIZE);
		int outboundQueue = NumberUtils.toInt(String.valueOf(outbound), 0);
		// connectionCount
		Object conCnt = comObj.get(HumeDashboardConstants.CONNECTION_COUNT);
		int connectionCnt = NumberUtils.toInt(String.valueOf(conCnt), 0);
		// receivedCount
		Object rxCnt = comObj.get(HumeDashboardConstants.COMPOINT_RECEIVE_COUNT);
		int receivedCnt = NumberUtils.toInt(String.valueOf(rxCnt), 0);
		// sentCount
		Object stCnt = comObj.get(HumeDashboardConstants.COMPOINT_SENT_COUNT);
		int sentCnt = NumberUtils.toInt(String.valueOf(stCnt), 0);
		
		CommPoint commpoint = new CommPoint();
		commpoint.setName(name);
		commpoint.setId(id);
		commpoint.setState(state);
		commpoint.setMode(mode);
		commpoint.setFolderPath(path);
		commpoint.setInputIdleTime(HumeDashboardUtils.convertDuraton(intime));
		commpoint.setOutputIdleTime(HumeDashboardUtils.convertDuraton(outtime));
		commpoint.setUptime(HumeDashboardUtils.convertDuraton(uptime));
		commpoint.setInQueueSize(inboundQueue);
		commpoint.setOutQueueSize(outboundQueue);
		commpoint.setConnectionCount(connectionCnt);
		commpoint.setReceivedCount(receivedCnt);
		commpoint.setSentCount(sentCnt);
		

		return commpoint;
	}

}
