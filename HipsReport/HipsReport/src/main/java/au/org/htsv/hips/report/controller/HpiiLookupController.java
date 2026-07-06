package au.org.htsv.hips.report.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import au.org.htsv.hips.report.entity.ExceptionBasicData;
import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.entity.HpiiDTO;
import au.org.htsv.hips.report.service.ExceptionReportService;
import au.org.htsv.hips.report.service.HpiiLookupService;
import au.org.htsv.hips.report.util.ExceptionReportConstants;
import au.org.htsv.hips.report.util.ExceptionReportUtils;

/**
 * @author js278
 *
 */
@Controller
public class HpiiLookupController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HpiiLookupController.class);

	@Autowired
	private HpiiLookupService hpiiLookupService;
	
	@Value("${hpii.exemption.duration.hours}")
	private int hours;

	// indidivaul HPI-I search
	@RequestMapping(value = "/singleHpii", method = RequestMethod.GET)
	public String individualHpii(@RequestParam(value="searchCheck", required=false, defaultValue="false") boolean checked, @RequestParam(value="firstName", required=false) String firstName, @RequestParam(value="lastName", required=false) String lastName, @RequestParam(value="ahpra", required=false) String ahpra, @RequestParam(value="providerNumber", required=false) String providerNumber, @RequestParam(value="facility", required=false) String facility, HttpSession session) {
		clearHpiiSession(session);
		if(checked) {
			//HpiiDTO dto = getHpii(lastName, firstName, ahpra, providerNumber);
			HpiiDTO dto = checkHpii(facility, lastName, firstName, ahpra, providerNumber);
			if(StringUtils.defaultString(dto.getHpii()).equals("")) {
				session.setAttribute(ExceptionReportConstants.ERRORS, "Can not find relevant HPI-I.");
			}else {
				session.setAttribute(ExceptionReportConstants.HPII_DATA, dto);
			}
		}else {
			// draw UI to be ready.....
			//clearHpiiSession(session);
		}
		return "singleHpiiPage";
	}
	
	// batch HPI-I search
	@RequestMapping(value = "/batchHpii", method = {RequestMethod.GET, RequestMethod.POST})
	public String batchHpii(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value="facility", required=false) String facility, HttpSession session, HttpServletRequest request) {
		clearHpiiSession(session);
		if(request.getMethod().equalsIgnoreCase("GET")) {
			
			// display default page and do nothing....


		}else if(request.getMethod().equalsIgnoreCase("POST")){
			// 1. validate uploaded file	
			if (file != null && !file.isEmpty()) {
	            String originalFilename = file.getOriginalFilename();
	            if (originalFilename != null) {
	                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
	                if ("csv".equalsIgnoreCase(fileExtension)) {
	                    // File extension is CSV, proceed with further processing
	                } else {
	                    // Invalid file extension
	                    session.setAttribute(ExceptionReportConstants.ERRORS, "Invalid file format. Please upload a CSV file.");
	                    return "batchHpiiPage"; // Redirect to the batchHpii endpoint to display the error message
	                }
	            } else {
	                // File name not found
	                session.setAttribute(ExceptionReportConstants.ERRORS, "File name not found. Please try again.");
	                return "batchHpiiPage"; // Redirect to the batchHpii endpoint to display the error message
	            }
	        } else {
	            // No file uploaded
	            session.setAttribute(ExceptionReportConstants.ERRORS, "No file uploaded. Please select a file to upload.");
	            return "batchHpiiPage"; // Redirect to the batchHpii endpoint to display the error message
	        }
			
			// 2. proccess HPI-I search
			List<HpiiDTO> dtos = new ArrayList<HpiiDTO>();
			int lineCount = 0;
            int hpiiCount = 0;
            if (file != null && !file.isEmpty()) {
	            try {
	            	// Create a BufferedReader to read the lines from the uploaded CSV file
	            	BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    lineCount++;
	                    String[] columns = line.split(",");
	                    
	                    if (!((columns.length == 3) || (columns.length == 4))) {
	                        // If a line doesn't have 3 columns, throw an error
	                        session.setAttribute(ExceptionReportConstants.ERRORS, "Invalid format on line " + lineCount);
	                        dtos = null;
	                        break;
	                    }
	                    if(lineCount ==1) continue; // skip header in csv
	                    String familyName = columns[0];
	                    String firstName = columns[1];
	                    String ahpraNumber = columns[2];
	                    String providerNumber = "";
	                    try {
	                    	providerNumber = StringUtils.defaultString(columns[3]);
	                    }catch(ArrayIndexOutOfBoundsException e) { // no provider number provided
	                    	
	                    }
	                    // search Hpii
	                    HpiiDTO dto = checkHpii(facility, StringUtils.defaultString(familyName), StringUtils.defaultString(firstName), StringUtils.defaultString(ahpraNumber), providerNumber);
	                    if(StringUtils.isNotBlank(dto.getHpii())) {
	            			hpiiCount++;
	            		}
	                    dtos.add(dto);
	                    LOG.error("[ CURRENT LINE - " + (lineCount-1) + "]");
	                }
	                System.out.println("[Total Stats : " + hpiiCount + "/" + (lineCount-1) + "]");
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
			session.setAttribute(ExceptionReportConstants.SUCCESS, hpiiCount);
			session.setAttribute(ExceptionReportConstants.TOTAL, (lineCount-1));// except 1st line header
			session.setAttribute(ExceptionReportConstants.HPII_LIST, dtos);
		}
		return "batchHpiiPage";
	}
	
	// clear all existing info in session
	private void clearHpiiSession(HttpSession session) {
		session.setAttribute(ExceptionReportConstants.HPII_DATA, null);
		session.setAttribute(ExceptionReportConstants.HPII_LIST, null);
		session.setAttribute(ExceptionReportConstants.ERRORS, null);
		session.setAttribute(ExceptionReportConstants.SUCCESS, null);
		session.setAttribute(ExceptionReportConstants.TOTAL, null);
	}
	
	/*
	// get HPI-I via DB/HIPS
	private HpiiDTO getHpii(String lastName, String firstName, String ahpra) {
		HpiiDTO clientData = new HpiiDTO(StringUtils.defaultString(lastName), StringUtils.defaultString(firstName), StringUtils.defaultString(ahpra));
		// 2. check if record exists in database
		HpiiDTO dbResult = hpiiLookupService.getHpiiFromDB(clientData);
		HpiiDTO hipsResult = null;
		boolean isExist = StringUtils.isNotBlank(dbResult.getHpii());
		if(isExist) {
			// 3-1. if exists, display info
			LOG.info("from db...{}", dbResult);
		}else {
			// 3-2-1. if not, make request to Hips
			hipsResult = hpiiLookupService.getHPII(clientData);
			LOG.info("from hips...{}", hipsResult);
			// check if hpii retrieved from HIPS or not
			boolean isBackFromHips = StringUtils.isNotBlank(hipsResult.getHpii());
			if(isBackFromHips) {
				// 3-2-2. save received data into database
				int inserted = hpiiLookupService.addHpiiToDB(hipsResult);
				LOG.info("to db...{}", inserted);
				// 4-1. if no update, return empty dto
				if(inserted == 0) return dbResult;
				// 4-2. if update is successful, go to next for returning info
				LOG.info("after db insert...{}", hipsResult);
			}
			
		}
		// 4. return either dbResult or hipsResult (if hipsResult is not null)
		return (hipsResult != null)? hipsResult : dbResult;
	}
	*/
	
	// get HPI-I via DB
	private HpiiDTO checkHpii(String hospital, String lastName, String firstName, String ahpra, String providerNumber) {
		// 1. prepare DTO
		String facilityCode = StringUtils.isBlank(hospital) ? ExceptionReportConstants.FACILITY_SAMPLE : hospital;
		HpiiDTO clientData = new HpiiDTO(facilityCode, StringUtils.defaultString(lastName), StringUtils.defaultString(firstName), StringUtils.defaultString(ahpra), StringUtils.defaultString(providerNumber));
		// 2. check if record exists in database
		HpiiDTO dbResult = hpiiLookupService.getHpiiFromDB(clientData);
		boolean isExist = StringUtils.isNotBlank(dbResult.getHpii());
		if(isExist) {
			// 3. if exists, check updated time is within retention period
			LOG.info("from db...{}", dbResult);
			LocalDateTime updatedTime = dbResult.getCreateDate();
			 if (isWithinLastConfigHours(updatedTime)) {
	               // updatedTime is within the last 48 hours, re-use it
				 return dbResult;
	         }else {
	        	 // data in Db is old then get latest info through Hips
	        	 return updateThroughHips(clientData);
	         }
		}else {
			// no data in Db then get info through Hips
			return insertThroughHips(clientData);
		}
	}
	
	// update DB via Hips 
	private HpiiDTO updateThroughHips(HpiiDTO clientData) {
		// 1. inovoke request to 
		HpiiDTO hipsResult = hpiiLookupService.getHPII(clientData);
		LOG.info("from hips...{}", hipsResult);
		// 2. check if hpii retrieved from HIPS or not
		boolean isBackFromHips = StringUtils.isNotBlank(hipsResult.getHpii());
		if(isBackFromHips) {
			// 3. update received data into database
			int updated = hpiiLookupService.updateHpiiToDB(hipsResult);
			LOG.info("to db...{}", updated);
			hipsResult.setCreateDate(LocalDateTime.now());
		} 
		
		/* NEVER HAPPENS IN UPDATE CONDITION
		else {// retrieving hpii failed
			// check if givename is multiple
			String givenName = hipsResult.getFirstName();
			String[] names = StringUtils.split(givenName, ' ');
			if(names.length>=2) {
				for (int i = names.length-1; i > 0; i--) {
		            String trial = concatenateNames(names, i);
		            HpiiDTO tempTrial = new HpiiDTO();
		            tempTrial.setFirstName(trial);
		            tempTrial.setLastName(clientData.getLastName());
		            tempTrial.setAhpra(clientData.getAhpra());
		            tempTrial.setHospitalCode(clientData.getHospitalCode());
		            // re-run with trial givenname
		            tempTrial = hpiiLookupService.getHPII(tempTrial);
		            boolean isBackAgain = StringUtils.isNotBlank(tempTrial.getHpii());
		            if(isBackAgain) {
		            	hipsResult = tempTrial;
		            	int inserted = hpiiLookupService.updateHpiiToDB(hipsResult);
		    			LOG.info("to db...{}", inserted);
		    			hipsResult.setCreateDate(LocalDateTime.now());
		    			break; // escape loop
		            }
		        }
			}
		}
		*/
		return hipsResult;
	}
	
	// add DB via Hips 
	private HpiiDTO insertThroughHips(HpiiDTO clientData) {
		// 1. inovoke request to 
		HpiiDTO hipsResult = hpiiLookupService.getHPII(clientData);
		LOG.info("from hips...{}", hipsResult);
		// 2. check if hpii retrieved from HIPS or not
		boolean isBackFromHips = StringUtils.isNotBlank(hipsResult.getHpii());
		if(isBackFromHips) { // hpii retreived successfully
			// 3. insert received data into database
			int inserted = hpiiLookupService.addHpiiToDB(hipsResult);
			LOG.info("to db...{}", inserted);
			hipsResult.setCreateDate(LocalDateTime.now());
		}
		
		/*
		else {// retrieving hpii failed
			
			// check if givename is multiple
			String givenName = hipsResult.getFirstName();
			String[] names = StringUtils.split(givenName, ' ');
			if(names.length>=2) {
				for (int i = names.length-1; i > 0; i--) {
		            String trial = concatenateNames(names, i);
		            //System.out.println("Trial: " + trial);
		            HpiiDTO tempTrial = new HpiiDTO();
		            tempTrial.setFirstName(trial);
		            tempTrial.setLastName(clientData.getLastName());
		            tempTrial.setAhpra(clientData.getAhpra());
		            tempTrial.setProviderNumber(clientData.getProviderNumber());
		            tempTrial.setHospitalCode(clientData.getHospitalCode());
		            // re-run with trial givenname
		            tempTrial = hpiiLookupService.getHPII(tempTrial);
		            boolean isBackAgain = StringUtils.isNotBlank(tempTrial.getHpii());
		            if(isBackAgain) {
		            	hipsResult = tempTrial;
		            	int inserted = hpiiLookupService.addHpiiToDB(hipsResult);
		    			LOG.info("to db...{}", inserted);
		    			hipsResult.setCreateDate(LocalDateTime.now());
		    			break; // escape loop
		            }
		        }
			}	
			
		}*/
		return hipsResult;
	}
	
	/*
	// concatenames
    private static String concatenateNames(String[] names, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(names[i]);
        }
        return sb.toString();
    }
	*/
	
	
	// check exemption time
	private boolean isWithinLastConfigHours(LocalDateTime updatedTime) {
        if (updatedTime == null) {
            return false;
        }
        // Get the current LocalDateTime
        LocalDateTime cutoff = LocalDateTime.now().minusHours(hours);
        return updatedTime.isAfter(cutoff);
    }

	
}
