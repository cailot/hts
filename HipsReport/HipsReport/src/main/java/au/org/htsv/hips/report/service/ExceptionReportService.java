package au.org.htsv.hips.report.service;

import java.util.List;

import au.org.htsv.hips.report.entity.ExceptionBasicData;
import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.entity.ExceptionListData;
import au.org.htsv.hips.report.entity.ExceptionSummaryData;
import au.org.htsv.hips.report.entity.ExceptionAuditData;

public interface ExceptionReportService {

	// show the brief upload for dashboard page
	ExceptionSummaryData getDashboard(ExceptionBasicData data);
	
	// show the count for summary page
	ExceptionSummaryData getSummary(ExceptionBasicData data);
	
	// show the list for detail page 
	List<ExceptionListData> getExceptionList(ExceptionBasicData data, String[] types);
	
	// show site list for specific acronym
	List<ExceptionSimpleData> getSiteList(String acronym);

	// show site all list
	List<ExceptionSimpleData> getSiteAllList();
	
	// show agency list
	List<ExceptionSimpleData> getAgencyList();

	// show the list for audit
	List<ExceptionAuditData> getAuditList(ExceptionBasicData data);
	
}
