package au.org.htsv.hips.report.dao;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import au.org.htsv.hips.report.entity.PatientData;
import au.org.htsv.hips.report.util.ExceptionReportConstants;

@Repository
public class AuxiliaryInformationDAOImpl implements AuxiliaryInformationDAO {

	@Autowired
	private EntityManager entityManager;

	@Value("${sql.retreive.original.message}")
	private String originalMessage;
	
	@Value("${sql.retreive.patient.info}")
	private String patientInfo;


	
	@Override
	public String retrieveMessage(String id) {
		String msg = (String) entityManager.createNativeQuery(originalMessage)
				.setParameter(ExceptionReportConstants.AUDIT_ID, id)
				.getSingleResult();
		return msg;
	}


	@Override
	public PatientData retrievePatient(String id) {
		Query query = entityManager.createNativeQuery(patientInfo);
		query.setParameter(ExceptionReportConstants.PATIENT_ID, id);
		// make sure only one record returned
		PatientData patient = null;
		try {		
			patient = new PatientData((Object[]) query.setMaxResults(1).getSingleResult());
		}catch(NoResultException e) {
			patient = new PatientData();
		}
		return patient;
	}
	
}
