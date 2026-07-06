package au.org.htsv.hips.report.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.org.htsv.hips.report.dao.AuxiliaryInformationDAO;
import au.org.htsv.hips.report.entity.PatientData;

@Service
public class AuxiliaryInformationServiceImpl implements AuxiliaryInformationService {
	
	@Autowired
	private AuxiliaryInformationDAO auxiliaryInformationDAO;
	
	@Override
	@Transactional
	public String getHL7Message(String id) {
		String msg = auxiliaryInformationDAO.retrieveMessage(id);
		return replaceLineBreak(msg);
	}

	@Override
	@Transactional
	public PatientData getPatientInfo(String id) {
		return auxiliaryInformationDAO.retrievePatient(id);
	}
	
	// simply replace line separator with <br> tag for HTML view
	private String replaceLineBreak(String str) {
		String updated = StringUtils.replace(str, System.lineSeparator(), "<br>");
		return updated;
	}
	
}
