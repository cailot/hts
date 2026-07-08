package au.org.htsv.hips.report.dao;


import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import au.org.htsv.hips.report.entity.PatientData;
import au.org.htsv.hips.report.util.ExceptionReportConstants;

@Repository
public class AuxiliaryInformationDAOImpl implements AuxiliaryInformationDAO {

	@Autowired
	private ArchiveAwareQueryExecutor queryExecutor;

	@Value("${sql.retreive.original.message}")
	private String originalMessage;
	
	@Value("${sql.retreive.patient.info}")
	private String patientInfo;


	
	@Override
	public String retrieveMessage(String id) {
		return queryExecutor.executeSingleResult(originalMessage,
				query -> query.setParameter(ExceptionReportConstants.AUDIT_ID, id));
	}


	@Override
	public PatientData retrievePatient(String id) {
		try {
			Object[] result = queryExecutor.executeSingleRow(patientInfo,
					query -> query.setParameter(ExceptionReportConstants.PATIENT_ID, id));
			return new PatientData(result);
		} catch (NoResultException e) {
			return new PatientData();
		}
	}
	
}
