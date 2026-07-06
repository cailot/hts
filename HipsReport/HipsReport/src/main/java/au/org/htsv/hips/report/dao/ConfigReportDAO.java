package au.org.htsv.hips.report.dao;

import java.util.List;
import java.util.Map;

import au.org.htsv.hips.report.entity.ExceptionSimpleData;

public interface ConfigReportDAO {

	// get site list for specific agency
	 List<ExceptionSimpleData> getSiteList(String name);

	// get all site list
	 List<ExceptionSimpleData> getSiteAllList();
	 
	// get all agency list
	 List<ExceptionSimpleData> getAgencyList();
	 
	// get hospital ID by facility code
	 int getHospitalId(String facility);
		 

}
