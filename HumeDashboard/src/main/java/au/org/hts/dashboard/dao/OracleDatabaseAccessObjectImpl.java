package au.org.hts.dashboard.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import au.org.hts.dashboard.entity.HL7Transaction;
import au.org.hts.dashboard.util.HumeDashboardConstants;



@Repository
public class OracleDatabaseAccessObjectImpl implements OracleDatabaseAccessObject {

	@Autowired
	private EntityManager entityManager;
	
	@Value("${sql.commpoint.log}")
	private String serviceLog;
	
	@Value("${sql.hl7.log}")
	private String hl7Log;
	
	private static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	@Override
	public List<HL7Transaction> getServiceLog(String service) {
		Query query = entityManager.createNativeQuery(serviceLog);
		query.setParameter(HumeDashboardConstants.COMMPOINT_NAME, service);
		List<Object[]> results = query.getResultList();
		List<HL7Transaction> logs = new ArrayList<HL7Transaction>(results.size());
		for(Object[] r: results) {
			HL7Transaction tx = new HL7Transaction();
			tx.setAuditId((r[0] != null) ? (String.valueOf(r[0])) : "0");
			// Safely convert Timestamp to formatted String
	        Object tsObject = r[1];
	        String lastUpdate = "";
	        if (tsObject instanceof Timestamp) {
	            Timestamp ts = (Timestamp) tsObject;
	            lastUpdate = ts.toLocalDateTime().format(TIME_FORMAT);
	        } else if (tsObject != null) {
	            lastUpdate = String.valueOf(tsObject);
	        }
	        tx.setLastUpdate(lastUpdate);
			tx.setSendingApp(StringUtils.defaultString((String) r[2], ""));
			tx.setReceivingApp(StringUtils.defaultString((String) r[3], ""));
			tx.setMsgType(StringUtils.defaultString((String) r[4], ""));
			tx.setMsgEvent(StringUtils.defaultString((String) r[5], ""));
			tx.setMsgId(StringUtils.defaultString((String) r[6], ""));
			tx.setPatientUr(StringUtils.defaultString((String) r[7], ""));
			tx.setPatientFirstName(StringUtils.defaultString((String) r[8], ""));
			tx.setPatientLastName(StringUtils.defaultString((String) r[9], ""));
			tx.setPatientDob(StringUtils.defaultString((String) r[10], ""));
			tx.setPatientGender(StringUtils.defaultString((String) r[11], ""));
			tx.setVisitingId(StringUtils.defaultString((String) r[12], ""));		
			logs.add(tx);
		}
		return logs;
	}

	@Override
	public String getHL7Message(Long id) throws SQLException, IOException {
		Object[] result = (Object[]) entityManager.createNativeQuery(hl7Log)
		    .setParameter(HumeDashboardConstants.LOG_NAME, id)
		    .getSingleResult();
		String message = clobToString((Clob) result[0]);
	    String ack = clobToString((Clob) result[1]);
		//return message + HumeDashboardConstants.HL7_LOG_SEPERATOR + ack;
	    return message + System.lineSeparator() + HumeDashboardConstants.HL7_LOG_SEPERATOR + System.lineSeparator() + ack;
	}
	
	
	// Convert Clob to String
	private String clobToString(Clob clob) throws SQLException, IOException {
	    if (clob == null) return "";
	    StringBuilder sb = new StringBuilder();
	    try (Reader reader = clob.getCharacterStream();
	         BufferedReader br = new BufferedReader(reader)) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line).append("\n");
	        }
	    }
	    return sb.toString();
	}
	
	/*
	
	@Override
	public String getHospitalName(String facility) throws NoResultException {
		String name = (String) entityManager.createNativeQuery(hospitalName)
				.setParameter(HumeDashboardConstants.FACILITY_CODE, facility)
				.getSingleResult();
		return name;
	}
	

	@Override
	public Organisation getOrganisationDetail(String facility) throws NoResultException {
		Query query = entityManager.createNativeQuery(orgDetail);
		query.setParameter(HumeDashboardConstants.FACILITY_CODE, facility);
		// make sure only one record returned
		Organisation agency = new Organisation();
		Object[] r = (Object[]) query.setMaxResults(1).getSingleResult();
		agency.setId((r[0] != null) ? (String.valueOf(r[0])) : "0");
		agency.setName(StringUtils.defaultString((String) r[1], ""));
		agency.setAcronym(StringUtils.defaultString((String) r[2], ""));
		agency.setPerson(StringUtils.defaultString((String) r[3], ""));
		agency.setContact(StringUtils.defaultString((String) r[4], ""));
		agency.setEmail(StringUtils.defaultString((String) r[5], ""));
		agency.setAddress(StringUtils.defaultString((String) r[6], ""));
		return agency;
	}
	
	
	@Override
	public List<Organisation> getOrganisationList(String portfolio) throws NoResultException {
		Query query = entityManager.createNativeQuery(orgNameList);
		query.setParameter(HumeDashboardConstants.PORTFOLIO_ID, portfolio);
		List<Object[]> results = query.getResultList();
		
		List<Organisation> list = new ArrayList<Organisation>(results.size());
		for(Object[] r: results) {
			Organisation agency = new Organisation();
			agency.setId((r[0] != null) ? (String.valueOf(r[0])) : "0");
			agency.setName(StringUtils.defaultString((String) r[1], ""));
			agency.setAcronym(StringUtils.defaultString((String) r[2], ""));
			agency.setPerson(StringUtils.defaultString((String) r[3], ""));
			agency.setContact(StringUtils.defaultString((String) r[4], ""));
			agency.setEmail(StringUtils.defaultString((String) r[5], ""));
			agency.setAddress(StringUtils.defaultString((String) r[6], ""));
			list.add(agency);
		}
		return list;
	}

	@Override
	public List<Facility> getHospitalList(String acronym) throws NoResultException {
		Query query = entityManager.createNativeQuery(acronymHospitals);
		query.setParameter(HumeDashboardConstants.ACRONYM, acronym);
		List<Object[]> results = query.getResultList();
		List<Facility> list = new ArrayList<Facility>(results.size());
		for(Object[] r: results) {
			Facility facility = new Facility();
			facility.setId((r[0] != null) ? Integer.parseInt(r[0].toString()) : 0);
			facility.setCode(StringUtils.defaultString((String) r[1], ""));
			facility.setName(StringUtils.defaultString((String) r[2], ""));
			list.add(facility);
		}
		return list;
	}
	
	@Override
	public List<Facility> getHospitalList(int id) throws NoResultException {
		Query query = entityManager.createNativeQuery(idHospitals);
		query.setParameter(HumeDashboardConstants.COMPONENT_ID, id);
		List<Object[]> results = query.getResultList();
		List<Facility> list = new ArrayList<Facility>(results.size());
		for(Object[] r: results) {
			Facility facility = new Facility();
			facility.setId((r[0] != null) ? Integer.parseInt(r[0].toString()) : 0);
			facility.setCode(StringUtils.defaultString((String) r[1], ""));
			facility.setName(StringUtils.defaultString((String) r[2], ""));
			list.add(facility);
		}
		return list;
	}
	

	@Override
	public List<HL7TransactionInfo> analyseHL7Messages(String facility, String fromDate, String toDate) throws NoResultException {
		Query query = entityManager.createNativeQuery(msgAnalyser);
		
		fromDate = HumeDashboardUtils.convertDateTimeFormat(fromDate);
		toDate = HumeDashboardUtils.convertDateTimeFormat(toDate);
		
		query.setParameter(HumeDashboardConstants.HOSPITAL_ID, facility);
		query.setParameter(HumeDashboardConstants.FROM_DATE_TIME, fromDate);
		query.setParameter(HumeDashboardConstants.TO_DATE_TIME, toDate);
		List<Object[]> results = query.getResultList();
		
		List<HL7TransactionInfo> list = new ArrayList<HL7TransactionInfo>(results.size());
		for(Object[] r: results) {
			HL7TransactionInfo info = new HL7TransactionInfo();
			//info.setFacility(facility);
			info.setMsgType(StringUtils.defaultString((String) r[1], ""));
			info.setMsgEvent(StringUtils.defaultString((String) r[2], ""));
			//info.setDirection(StringUtils.defaultString((String) r[3], ""));
			//info.setMsgCount((r[4] != null) ? r[4].toString() : "0");
			info.setMsgId((r[5] != null) ? r[5].toString() : "0");
			info.setLastUpdate(StringUtils.defaultString((String) r[6], ""));
			
			//info.setPatientUR(StringUtils.defaultString((String) r[7], ""));
			info.setPatientFirstName(StringUtils.defaultString((String) r[8], ""));
			info.setPatientLastName(StringUtils.defaultString((String) r[9], ""));
			info.setPatientDob(StringUtils.defaultString((String) r[10], ""));
			info.setPatientGender(StringUtils.defaultString((String) r[11], ""));
			
			list.add(info);
		}
		return list;
	}
	*/
}
