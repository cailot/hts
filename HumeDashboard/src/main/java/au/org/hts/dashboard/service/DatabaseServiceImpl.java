package au.org.hts.dashboard.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.org.hts.dashboard.dao.OracleDatabaseAccessObject;
import au.org.hts.dashboard.entity.HL7Transaction;

@Service
public class DatabaseServiceImpl implements DatabaseService {

	@Autowired
	private OracleDatabaseAccessObject oracleDatabaseAccessObject;

	
	/*
	@Override
	@Transactional
	public String getMsgShortDescription(String msgType, String msgEvent) {
		String result = "";
		try {
			result = oracleDatabaseAccessObject.getMsgShortDescription(msgType, msgEvent);
		}catch(NoResultException e) {
			
		}
		return result;
	}
	
	@Override
	@Transactional
	public Organisation getOrganisationDetail(String facility) {
		Organisation org = new Organisation();
		try {
			org = oracleDatabaseAccessObject.getOrganisationDetail(facility);
		}catch(NoResultException e) {
			
		}
		return org;
	}
	
	@Override
	@Transactional
	public String getHospitalName(String facility) {
		String result = "";
		try {
			result = oracleDatabaseAccessObject.getHospitalName(facility);
		}catch(NoResultException e) {
			
		}
		return result;
	}

	@Override
	@Transactional
	public List<HL7TransactionInfo> getHL7Transactions(String facility, String from, String to) {
		List<HL7TransactionInfo> info = null;
		try {
			info = oracleDatabaseAccessObject.analyseHL7Messages(facility, from, to);
		}catch(NoResultException e) {
			
		}
		return info;
	}
	
	
	//@Override
	//@Transactional
	//public List<Organisation> getOrganisationList(String portfolio) {
	//	return dashboardConfigDao.getOrganisationList(portfolio);
	//}

	@Override
	@Transactional
	public List<HospitalStats> getTransactionStats(String organisation) {
		//return htsDashboardDao.getTransactionStats(facility);
		
		// 1. get hospital list
		List<Facility> facilities = oracleDatabaseAccessObject.getHospitalList(organisation);
		
		// 2. populate HospitalStats object per hospital
		List<HospitalStats> hospitals = new ArrayList<HospitalStats>();
		for(Facility facility : facilities) {
			HospitalStats hospital = new HospitalStats();
			String code = facility.getCode();
			String name = facility.getName();
			hospital.setCode(code);
			hospital.setName(name);
			
			
			
			List<StatsInfo> stats = getStatsDummy(code); // need to point DAO method
			
			for(StatsInfo stat : stats) {
				if(StringUtils.equalsIgnoreCase("RECEIVED", stat.getDirection())){
					hospital.getReceivedStats().add(stat);
				}else if(StringUtils.equalsIgnoreCase("SENT", stat.getDirection())){
					hospital.getSentStats().add(stat);
				}
			}
			
			
			
			//HospitalStats hospital = new HospitalStats(code, name, stats);
			hospitals.add(hospital);
		}
		
		// 3. return list of hospitals
		return hospitals;
	}
	
	
	private List<StatsInfo> getStatsDummy(String facility)
	{
		List<StatsInfo> stats = new ArrayList<StatsInfo>();
		for(int i=0; i<30; i++) {
			int random1 = (int)(Math.random()*30 + 0);
			int random2 = (int)(Math.random()*30 + 0);
			stats.add(new StatsInfo(facility,"RECEIVED", "12:"+i, random1, random2));
			stats.add(new StatsInfo(facility,"SENT", "12:"+i, random2, random1));
			
		}
		return stats;
	}

	@Override
	@Transactional
	public List<Facility> getHospitalList(String acronym) {
		List<Facility> facility = null;
		try {
			facility = oracleDatabaseAccessObject.getHospitalList(acronym);
		}catch(NoResultException e) {
			
		}
		return facility;
	}

	@Override
	@Transactional
	public List<Facility> getHospitalList(int orgId) {
		List<Facility> facility = null;
		try {
			facility = oracleDatabaseAccessObject.getHospitalList(orgId);
		}catch(NoResultException e) {
			
		}
		return facility;
	}
	
	*/

	@Override
	public List<HL7Transaction> getTransactionLog(String service) {
		List<HL7Transaction> info = new ArrayList<>();
		try {
			info = oracleDatabaseAccessObject.getServiceLog(service);
		}catch(NoResultException e) {
			
		}
		return info;
	}


	@Override
	public String getLogDetail(Long id) {
		String msg = "";
		try {
			msg = oracleDatabaseAccessObject.getHL7Message(id);
		}catch(NoResultException | SQLException | IOException e) {
			
		}
		return msg;
	}

	
	
	
	
	
}
