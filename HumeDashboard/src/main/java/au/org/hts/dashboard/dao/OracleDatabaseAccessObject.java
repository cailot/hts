package au.org.hts.dashboard.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;

import au.org.hts.dashboard.entity.HL7Transaction;


public interface OracleDatabaseAccessObject {
	
	
	List<HL7Transaction> getServiceLog(String service);
	
	String getHL7Message(Long id) throws SQLException, IOException;
	
	/*
	// get message short description by msg type & msg event
	String getMsgShortDescription(String msgType, String msgEvent) throws NoResultException;
	
	// retreive organisation list based on the portfolio
	List<Organisation> getOrganisationList(String portfolio) throws NoResultException;
	
	// retreive hospital list based on the organisation acronym
	List<Facility> getHospitalList(String acronym) throws NoResultException;
	
	// retreive hospital list based on the organisation Id
	List<Facility> getHospitalList(int orgId) throws NoResultException;

	// get the specific Organisation info by facility code
	Organisation getOrganisationDetail(String facility) throws NoResultException;
	
	// get hospital name by facility code
	String getHospitalName(String facility) throws NoResultException;
	
	//List<HL7TransactionInfo> analyseHL7Messages(String facility, String fromDate, String toDate) throws NoResultException;
	
	 */
}
