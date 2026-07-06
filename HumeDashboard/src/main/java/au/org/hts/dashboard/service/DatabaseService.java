package au.org.hts.dashboard.service;

import java.util.List;

import au.org.hts.dashboard.entity.HL7Transaction;

public interface DatabaseService {
	
	
	List<HL7Transaction> getTransactionLog(String service);
	
	String getLogDetail(Long id);
	
}
