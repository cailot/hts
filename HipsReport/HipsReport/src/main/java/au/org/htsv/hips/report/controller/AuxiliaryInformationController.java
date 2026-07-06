package au.org.htsv.hips.report.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.org.htsv.hips.report.entity.PatientData;
import au.org.htsv.hips.report.service.AuxiliaryInformationService;

/**
 * @author js278
 *
 */
@RestController
public class AuxiliaryInformationController {

	@Autowired
	private AuxiliaryInformationService auxiliaryInformationService;
	
	private static final Logger LOG = LoggerFactory.getLogger(AuxiliaryInformationController.class);
	
	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value="/message", method=RequestMethod.GET)
	public String displayHL7Message( @RequestParam(value="id", required=false, defaultValue="1") String id) {
		String detail = auxiliaryInformationService.getHL7Message(id);
		return detail;
	}
	
	@RequestMapping(value="/patient", method=RequestMethod.GET)
	public String displayPatient( @RequestParam(value="id", required=false, defaultValue="1") String id) {
		PatientData patient = auxiliaryInformationService.getPatientInfo(id);
		String detail = "";
		try {
			detail = mapper.writeValueAsString(patient);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
		}
		return detail;
	}
}


