package au.org.htsv.hips.report.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import au.org.htsv.hips.report.entity.ExceptionAuditData;
import au.org.htsv.hips.report.entity.ExceptionListData;
import au.org.htsv.hips.report.util.ExceptionReportConstants;

@Repository
public class RhapsodyReportDAOImpl implements RhapsodyReportDAO {

	@Autowired
	private EntityManager entityManager;

	//////////////////////////////////////////////
	//			Success Upload Count			//
	//////////////////////////////////////////////
	
	@Value("${sql.total.success.count.discharge}")
	private String totalSuccessCountDischarge;
	
	@Value("${sql.total.success.count.lis}")
	private String totalSuccessCountLis;
	
	@Value("${sql.total.success.count.ris}")
	private String totalSuccessCountRis;
	
	@Value("${sql.total.success.count.psml}")
	private String totalSuccessCountPsml;
	
	@Value("${sql.total.success.count.shs}")
	private String totalSuccessCountShs;
	
	@Value("${sql.total.success.count.es}")
	private String totalSuccessCountEs;
	
	
	//////////////////////////////////////////////
	//			Summary Count					//
	//////////////////////////////////////////////

	@Value("${sql.count.total.discharge.aie.hts}")
	private String totalCountDischargeAie2Hts;
	
	@Value("${sql.count.total.lis.aie.hts}")
	private String totalCountLisAie2Hts;
	
	@Value("${sql.count.total.ris.aie.hts}")
	private String totalCountRisAie2Hts;

	@Value("${sql.count.total.psml.aie.hts}")
	private String totalCountPsmlAie2Hts;
	
	@Value("${sql.count.total.shs.aie.hts}")
	private String totalCountShsAie2Hts;
	
	@Value("${sql.count.total.es.aie.hts}")
	private String totalCountEsAie2Hts;
	
	@Value("${sql.count.total.discharge.hts.mhr}")
	private String totalCountDischargeHts2Mhr;
	
	@Value("${sql.count.total.lis.hts.mhr}")
	private String totalCountLisHts2Mhr;
	
	@Value("${sql.count.total.ris.hts.mhr}")
	private String totalCountRisHts2Mhr;
	
	@Value("${sql.count.total.psml.hts.mhr}")
	private String totalCountPsmlHts2Mhr;
	
	@Value("${sql.count.total.shs.hts.mhr}")
	private String totalCountShsHts2Mhr;
	
	@Value("${sql.count.total.es.hts.mhr}")
	private String totalCountEsHts2Mhr;
	
	@Value("${sql.count.error.discharge.b4.hsie}")
	private String errorCountDischargeB4Hsie;
	
	@Value("${sql.count.error.lis.b4.hsie}")
	private String errorCountLisB4Hsie;
	
	@Value("${sql.count.error.ris.b4.hsie}")
	private String errorCountRisB4Hsie;

	@Value("${sql.count.error.psml.b4.hsie}")
	private String errorCountPsmlB4Hsie;
	
	@Value("${sql.count.error.shs.b4.hsie}")
	private String errorCountShsB4Hsie;
	
	@Value("${sql.count.error.es.b4.hsie}")
	private String errorCountEsB4Hsie;

	@Value("${sql.count.error.discharge.at.hts}")
	private String errorCountDischargeHts;
	
	@Value("${sql.count.error.lis.at.hts}")
	private String errorCountLisHts;
	
	@Value("${sql.count.error.ris.at.hts}")
	private String errorCountRisHts;

	@Value("${sql.count.error.psml.at.hts}")
	private String errorCountPsmlHts;
	
	@Value("${sql.count.error.shs.at.hts}")
	private String errorCountShsHts;
	
	@Value("${sql.count.error.es.at.hts}")
	private String errorCountEsHts;
	
	@Value("${sql.count.error.discharge.at.hips}")
	private String errorCountDischargeHips;
	
	@Value("${sql.count.error.lis.at.hips}")
	private String errorCountLisHips;
	
	@Value("${sql.count.error.ris.at.hips}")
	private String errorCountRisHips;
	
	@Value("${sql.count.error.psml.at.hips}")
	private String errorCountPsmlHips;
	
	@Value("${sql.count.error.shs.at.hips}")
	private String errorCountShsHips;
	
	@Value("${sql.count.error.es.at.hips}")
	private String errorCountEsHips;
	
	@Value("${sql.count.notok.lis.upload}")
	private String notOkCountLisUpload;
	
	@Value("${sql.count.notok.ris.upload}")
	private String notOkCountRisUpload;

	
	//////////////////////////////////////////////
	//		Exception Detail					//
	//////////////////////////////////////////////

	@Value("${sql.list.exception.detail.b4.hsie}")
	private String listDetailB4Hsie;
	
	@Value("${sql.list.exception.detail.at.hts}")
	private String listDetailAtHts;
	
	@Value("${sql.list.exception.detail.at.hips}")
	private String listDetailAtHips;

	@Value("${sql.list.exception.detail.notok}")
	private String listDetailNotOk;
	
	
	//////////////////////////////////////////////
	//		Audit Info							//
	//////////////////////////////////////////////
	
	@Value("${sql.list.audit.detail}")
	private String listAudit;
	
	@Value("${sql.audit.additional.query}")
	private String auditAddionalQuery;

	
		
	///////////////////////////////////////////////// Dashboard //////////////////////////////////////////////////
	
	@Override
	public int successCountDischargeUpload(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalSuccessCountDischarge, from, to, hospitalIds);	
	}
	
	@Override
	public int successCountLisUpload(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalSuccessCountLis, from, to, hospitalIds);	
	}

	@Override
	public int successCountRisUpload(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalSuccessCountRis, from, to, hospitalIds);	
	}
	
	@Override
	public int successCountPsmlUpload(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalSuccessCountPsml, from, to, hospitalIds);	
	}
	
	@Override
	public int successCountShsUpload(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalSuccessCountShs, from, to, hospitalIds);	
	}

	@Override
	public int successCountEsUpload(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalSuccessCountEs, from, to, hospitalIds);	
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	
	
	
	
	//////////////////////////////////////////////////// Summary //////////////////////////////////////////////////////
	
	@Override
	public int totalCountDischargeAie2Hts(String from, String to, String facility) {
		return getCountByFacility(totalCountDischargeAie2Hts, from, to, facility);
	}

	@Override
	public int totalCountLisAie2Hts(String from, String to, String facility) {
		return getCountByFacility(totalCountLisAie2Hts, from, to, facility);
	}

	@Override
	public int totalCountRisAie2Hts(String from, String to, String facility) {
		return getCountByFacility(totalCountRisAie2Hts, from, to, facility);
	}
	
	@Override
	public int totalCountPsmlAie2Hts(String from, String to, String facility) {
		return getCountByFacility(totalCountPsmlAie2Hts, from, to, facility);
	}

	@Override
	public int totalCountShsAie2Hts(String from, String to, String facility) {
		return getCountByFacility(totalCountShsAie2Hts, from, to, facility);
	}

	@Override
	public int totalCountEsAie2Hts(String from, String to, String facility) {
		return getCountByFacility(totalCountEsAie2Hts, from, to, facility);
	}

	@Override
	public int totalCountDischargeHts2Mhr(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalCountDischargeHts2Mhr, from, to, hospitalIds);
	}

	@Override
	public int totalCountLisHts2Mhr(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalCountLisHts2Mhr, from, to, hospitalIds);
	}

	@Override
	public int totalCountRisHts2Mhr(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalCountRisHts2Mhr, from, to, hospitalIds);
	}
	
	@Override
	public int totalCountPsmlHts2Mhr(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalCountPsmlHts2Mhr, from, to, hospitalIds);
	}

	@Override
	public int totalCountShsHts2Mhr(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalCountShsHts2Mhr, from, to, hospitalIds);
	}

	@Override
	public int totalCountEsHts2Mhr(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(totalCountEsHts2Mhr, from, to, hospitalIds);
	}

	@Override
	public int errorCountDischargeB4Hsie(String from, String to, String facility) {
		return getCountByFacility(errorCountDischargeB4Hsie, from, to, facility);
	}

	@Override
	public int errorCountLisB4Hsie(String from, String to, String facility) {
		return getCountByFacility(errorCountLisB4Hsie, from, to, facility);
	}

	@Override
	public int errorCountRisB4Hsie(String from, String to, String facility) {
		return getCountByFacility(errorCountRisB4Hsie, from, to, facility);
	}

	@Override
	public int errorCountPsmlB4Hsie(String from, String to, String facility) {
		return getCountByFacility(errorCountPsmlB4Hsie, from, to, facility);
	}

	@Override
	public int errorCountShsB4Hsie(String from, String to, String facility) {
		return getCountByFacility(errorCountShsB4Hsie, from, to, facility);
	}

	@Override
	public int errorCountEsB4Hsie(String from, String to, String facility) {
		return getCountByFacility(errorCountEsB4Hsie, from, to, facility);
	}
	
	@Override
	public int errorCountDischargeAtHts(String from, String to, String facility) {
		return getCountByFacility(errorCountDischargeHts, from, to, facility);
	}

	@Override
	public int errorCountLisAtHts(String from, String to, String facility) {
		return getCountByFacility(errorCountLisHts, from, to, facility);
	}

	@Override
	public int errorCountRisAtHts(String from, String to, String facility) {
		return getCountByFacility(errorCountRisHts, from, to, facility);
	}
	
	@Override
	public int errorCountPsmlAtHts(String from, String to, String facility) {
		return getCountByFacility(errorCountPsmlHts, from, to, facility);
	}

	@Override
	public int errorCountShsAtHts(String from, String to, String facility) {
		return getCountByFacility(errorCountShsHts, from, to, facility);
	}

	@Override
	public int errorCountEsAtHts(String from, String to, String facility) {
		return getCountByFacility(errorCountEsHts, from, to, facility);
	}


	@Override
	public int errorCountDischargeAtHips(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(errorCountDischargeHips, from, to, hospitalIds);
	}

	@Override
	public int errorCountLisAtHips(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(errorCountLisHips, from, to, hospitalIds);
	}

	@Override
	public int errorCountRisAtHips(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(errorCountRisHips, from, to, hospitalIds);
	}
	
	@Override
	public int errorCountPsmlAtHips(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(errorCountPsmlHips, from, to, hospitalIds);
	}

	@Override
	public int errorCountShsAtHips(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(errorCountShsHips, from, to, hospitalIds);
	}

	@Override
	public int errorCountEsAtHips(String from, String to, List<String> hospitalIds) {
		return getCountByHospital(errorCountEsHips, from, to, hospitalIds);
	}

	@Override
	public int notOkCountLisUpload(String from, String to, String facility) {
		return getCountByFacility(notOkCountLisUpload, from, to, facility);	
	}
	
	@Override
	public int notOkCountRisUpload(String from, String to, String facility) {
		return getCountByFacility(notOkCountRisUpload, from, to, facility);	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	//////////////////////////////////////////////////// Detail //////////////////////////////////////////////////////
	
	@Override
	public List<ExceptionListData> listExceptionAtBeginOfHsie(String from, String to, String facility, List<String> documentNames) {
		return getListByFacility(listDetailB4Hsie, from, to, facility, documentNames);
	}		

	@Override
	public List<ExceptionListData> listExceptionAtHts(String from, String to, String facility, List<String> documentNames) {
		return getListByFacility(listDetailAtHts, from, to, facility, documentNames);
	}
	
	@Override
	public List<ExceptionListData> listExceptionAtMhr(String from, String to, List<String> hospitalIds, List<Integer> documentIds) {
		return getListByHospital(listDetailAtHips, from, to, hospitalIds, documentIds);
	}

	@Override
	public List<ExceptionListData> listNotOkHts2Mhr(String from, String to, String facility, List<String> documentNames) {
		return getListByFacility(listDetailNotOk, from, to, facility, documentNames);
	}
	

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	@Override
	public List<ExceptionAuditData> listAudit(String patient, String from, String to, String facility) {
		if(StringUtils.isBlank(patient)) {
			return getAuditInfo(listAudit, from, to, facility);
		}else {
			String query = listAudit + " " + auditAddionalQuery;
			return getAuditInfo(query, patient, from, to, facility);
		}
	}


	
	// return the count using facility codes
	private int getCountByFacility(String sql, String from, String to, String facility) {
		List<String> facilities = getFacilityCodes(facility);
		Integer count = (Integer) entityManager.createNativeQuery(sql)
				.setParameter(ExceptionReportConstants.FROM_DATE, from)
				.setParameter(ExceptionReportConstants.TO_DATE, to)
				.setParameter(ExceptionReportConstants.FACILITY_CODE, facilities)
				.getSingleResult();
		return count.intValue();
	}
	
	// return the count using hospitalIds
	private int getCountByHospital(String sql, String from, String to, List<String> hospitalIds) {
		Integer count = (Integer) entityManager.createNativeQuery(sql)
				.setParameter(ExceptionReportConstants.FROM_DATE, from)
				.setParameter(ExceptionReportConstants.TO_DATE, to)
				.setParameter(ExceptionReportConstants.HOSPITAL_ID, hospitalIds)
				.getSingleResult();
		return count.intValue();
	}
	
	// return exception list using facility codes
	private List<ExceptionListData> getListByFacility(String sql, String from, String to, String facility, List<String> documentNames) {
		List<String> facilities = getFacilityCodes(facility);
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(ExceptionReportConstants.FACILITY_CODE, facilities);
		query.setParameter(ExceptionReportConstants.DOCUMENT_NAME, documentNames);
		query.setParameter(ExceptionReportConstants.FROM_DATE, from);
		query.setParameter(ExceptionReportConstants.TO_DATE, to);
		List<Object[]> results = query.getResultList();
		List<ExceptionListData> list = new ArrayList<ExceptionListData>(results.size());
		for (Object[] r : results) {
			ExceptionListData data = new ExceptionListData(r);
			data.setFromDate(from);
			data.setToDate(to);
			data.setFacility(facility);
			list.add(data);
		}
		return list;
	}
	
	// return exception list using facility codes
	private List<ExceptionListData> getListByHospital(String sql, String from, String to, List<String> hospitalIds, List<Integer> documentIds) {
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(ExceptionReportConstants.HOSPITAL_ID, hospitalIds);
		query.setParameter(ExceptionReportConstants.DOCUMENT_ID, documentIds);
		query.setParameter(ExceptionReportConstants.FROM_DATE, from);
		query.setParameter(ExceptionReportConstants.TO_DATE, to);
		List<Object[]> results = query.getResultList();
		List<ExceptionListData> list = new ArrayList<ExceptionListData>(results.size());
		for (Object[] r : results) {
			ExceptionListData data = new ExceptionListData(r);
			data.setFromDate(from);
			data.setToDate(to);
			//data.setFacility(facility);
			list.add(data);
		}
		return list;
	}
		
	
	
	
	
	private List<ExceptionAuditData> getAuditInfo(String sql, String from, String to, String facility) {
		List<String> facilities = getFacilityCodes(facility);
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(ExceptionReportConstants.FACILITY_CODE, facilities);
		query.setParameter(ExceptionReportConstants.FROM_DATE, from);
		query.setParameter(ExceptionReportConstants.TO_DATE, to);
		List<Object[]> results = query.getResultList();
		List<ExceptionAuditData> list = new ArrayList<ExceptionAuditData>(results.size());
		for (Object[] r : results) {
			ExceptionAuditData data = new ExceptionAuditData(r);
			data.setFromDate(from);
			data.setToDate(to);
			data.setFacility(facility);
			list.add(data);
		}
		return list;
	}
	
	private List<ExceptionAuditData> getAuditInfo(String sql, String patient, String from, String to, String facility) {
		List<String> facilities = getFacilityCodes(facility);
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(ExceptionReportConstants.FACILITY_CODE, facilities);
		query.setParameter(ExceptionReportConstants.FROM_DATE, from);
		query.setParameter(ExceptionReportConstants.TO_DATE, to);
		query.setParameter(ExceptionReportConstants.PATIENT_INFO, patient);
		List<Object[]> results = query.getResultList();
		List<ExceptionAuditData> list = new ArrayList<ExceptionAuditData>(results.size());
		for (Object[] r : results) {
			ExceptionAuditData data = new ExceptionAuditData(r);
			data.setFromDate(from);
			data.setToDate(to);
			data.setFacility(facility);
			list.add(data);
		}
		return list;
	}
	
	private List<String> getFacilityCodes(String facility) {
		List<String> codes = new ArrayList<String>();
		if(StringUtils.contains(facility, ",")) {
			String[] sites = StringUtils.split(facility, ",");
			for(String site : sites) {
				codes.add(site);
			}
		}else {
			codes.add(facility);
		}
		return codes;
	}

	
}
