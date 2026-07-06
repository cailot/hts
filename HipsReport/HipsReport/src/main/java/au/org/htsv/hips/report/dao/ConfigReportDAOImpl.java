package au.org.htsv.hips.report.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.util.ExceptionReportConstants;

@Repository
public class ConfigReportDAOImpl implements ConfigReportDAO {

	@Autowired
	private EntityManager entityManager;

	@Value("${sql.report.site.list}")
	private String siteList;
	
	@Value("${sql.report.site.list.all}")
	private String siteListAll;
	
	@Value("${sql.report.agency.list}")
	private String agencyList;
	
	@Value("${sql.hospital.id.by.facility.code}")
	private String hospitalID;

	@Override
	public List<ExceptionSimpleData> getSiteList(String name) {
		Query query = entityManager.createNativeQuery(siteList);
		query.setParameter(ExceptionReportConstants.AGENCY_NAME, name);
		List<String> results = query.getResultList();
		List<ExceptionSimpleData> list = new ArrayList<ExceptionSimpleData>(results.size());
		for (String description : results) {
			ExceptionSimpleData data = new ExceptionSimpleData();
			String[] result = StringUtils.split(description, "-", 2);
			data.setValue(result[0]);
			data.setDisplay(result[1]);
			list.add(data);
		}
		return list;
	}


	@Override
	public List<ExceptionSimpleData> getSiteAllList() {
		Query query = entityManager.createNativeQuery(siteListAll);
		List<String> results = query.getResultList();
		List<ExceptionSimpleData> list = new ArrayList<ExceptionSimpleData>(results.size());
		for (String description : results) {
			ExceptionSimpleData data = new ExceptionSimpleData();
			String[] result = StringUtils.split(description, "-", 2);
			data.setValue(result[0]);
			data.setDisplay(result[1]);
			list.add(data);
		}
		return list;
	}


	@Override
	public List<ExceptionSimpleData> getAgencyList() {
		Query query = entityManager.createNativeQuery(agencyList);
		List<String> results = query.getResultList();
		List<ExceptionSimpleData> list = new ArrayList<ExceptionSimpleData>(results.size());
		for (String description : results) {
			ExceptionSimpleData data = new ExceptionSimpleData();
			data.setDisplay(description);
			list.add(data);
		}
		return list;
	}


	@Override
	public int getHospitalId(String facility) {
		Integer count = (Integer) entityManager.createNativeQuery(hospitalID)
				.setParameter(ExceptionReportConstants.FACILITY_CODE, facility)
				.getSingleResult();
		return count.intValue();
	}


}
