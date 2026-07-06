package au.org.htsv.hips.report.service;

import au.org.htsv.hips.report.entity.PatientData;

public interface AuxiliaryInformationService {

	// get the original HL7 message
	String getHL7Message(String id);
	
	// get the patient information
	PatientData getPatientInfo(String id);
}
