package au.org.htsv.hips.report.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.org.htsv.hips.report.dao.ConfigReportDAO;
import au.org.htsv.hips.report.dao.RhapsodyReportDAO;
import au.org.htsv.hips.report.entity.ExceptionAuditData;
import au.org.htsv.hips.report.entity.ExceptionBasicData;
import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.entity.ExceptionListData;
import au.org.htsv.hips.report.entity.ExceptionSummaryData;
import au.org.htsv.hips.report.util.ExceptionReportConstants;
import au.org.htsv.hips.report.util.ExceptionReportUtils;

@Service
public class ExceptionReportServiceImpl implements ExceptionReportService {
	
	@Autowired
	private RhapsodyReportDAO rhapsodyDAO;
	
	@Autowired
	private ConfigReportDAO configDAO;
	
	@Override
	@Transactional
	public ExceptionSummaryData getDashboard(ExceptionBasicData data) {
		String from = data.getFromDate();
		String to = data.getToDate();
		from += ExceptionReportConstants.FROM_DATE_SUFFIX;
		to += ExceptionReportConstants.TO_DATE_SUFFIX;
		String campus = data.getCampus();
		String hospital = data.getHospital();
		String facility = data.getFacility();
		
		ExceptionSummaryData summary = new ExceptionSummaryData(from, to, campus, hospital, facility);
		// get hospitalID by facility code
		List<String> hospitalIds = getHospitalIds(facility);
		
		int dischargeUpload = rhapsodyDAO.successCountDischargeUpload(from, to, hospitalIds);
		int lisUpload = rhapsodyDAO.successCountLisUpload(from, to, hospitalIds);
		int risUpload = rhapsodyDAO.successCountRisUpload(from, to, hospitalIds);
		int psmlUpload = rhapsodyDAO.successCountPsmlUpload(from, to, hospitalIds);
		int shsUpload = rhapsodyDAO.successCountShsUpload(from, to, hospitalIds);
		int esUpload = rhapsodyDAO.successCountEsUpload(from, to, hospitalIds);
		
		summary.setSuccessDischargeCntUpload(dischargeUpload);
		summary.setSuccessLisCntUpload(lisUpload);
		summary.setSuccessRisCntUpload(risUpload);
		summary.setSuccessPsmlCntUpload(psmlUpload);
		summary.setSuccessShsCntUpload(shsUpload);
		summary.setSuccessEsCntUpload(esUpload);

		return summary;
	}
	
	@Override
	@Transactional
	public ExceptionSummaryData getSummary(ExceptionBasicData data) {
		String from = data.getFromDate();
		String to = data.getToDate();
		from += ExceptionReportConstants.FROM_DATE_SUFFIX;
		to += ExceptionReportConstants.TO_DATE_SUFFIX;
		String campus = data.getCampus();
		String hospital = data.getHospital();
		String facility = data.getFacility();
		
		ExceptionSummaryData summary = new ExceptionSummaryData(from, to, campus, hospital, facility);
		// get hospitalID by facility code
		List<String> hospitalIds = getHospitalIds(facility);
		
		// Discharge Summary Info
		// 1. AIE to HTS
		int dischargeCntAie2Hsie = rhapsodyDAO.totalCountDischargeAie2Hts(from, to, facility);
		summary.setDischargeCntAie2Hts(dischargeCntAie2Hsie);
		if(dischargeCntAie2Hsie > 0) {
			// 2. HTS to MHR
			int dischargeCntHts2Mhr = rhapsodyDAO.totalCountDischargeHts2Mhr(from, to, hospitalIds);
			summary.setDischargeCntHts2Mhr(dischargeCntHts2Mhr);
			// 3. success count from HIPS to MHR
			int successDischargeCntUpload = rhapsodyDAO.successCountDischargeUpload(from, to, hospitalIds);
			summary.setSuccessDischargeCntUpload(successDischargeCntUpload);
			// 4-1. error count before HSIE - Ack is not AA
			int errorDischargeB4Hsie = rhapsodyDAO.errorCountDischargeB4Hsie(from, to, facility);
			// 4-2. error count at HTS
			int errorDischargeHts = rhapsodyDAO.errorCountDischargeAtHts(from, to, facility);
			summary.setDischargeErrorCntAtHts(errorDischargeB4Hsie + errorDischargeHts);
			// 5. error count at HIPS
			int errorDischargeHips = rhapsodyDAO.errorCountDischargeAtHips(from, to, hospitalIds);
			summary.setDischargeErrorCntAtHips(errorDischargeHips);
		}
		
		// Pathology Info
		// 1. AIE to HTS
		int lisCntAie2Hsie = rhapsodyDAO.totalCountLisAie2Hts(from, to, facility);
		summary.setLisCntAie2Hts(lisCntAie2Hsie);
		if(lisCntAie2Hsie > 0) {
			// 2. HTS to MHR
			int lisCntHts2Mhr = rhapsodyDAO.totalCountLisHts2Mhr(from, to, hospitalIds);
			summary.setLisCntHts2Mhr(lisCntHts2Mhr);
			// 3. success count from HIPS to MHR
			int successLisCntUpload = rhapsodyDAO.successCountLisUpload(from, to, hospitalIds);
			summary.setSuccessLisCntUpload(successLisCntUpload);
			// 4-1. error count before HSIE - Ack is not AA
			int errorLisB4Hsie = rhapsodyDAO.errorCountLisB4Hsie(from, to, facility);
			// 4-2. error count at HSIE
			int errorLisHts = rhapsodyDAO.errorCountLisAtHts(from, to, facility);
			// 4-3. IHI lookup failed- not included in PcehrAudit table
			int errorNotOkLis = rhapsodyDAO.notOkCountLisUpload(from, to, facility);
			summary.setLisErrorCntAtHts(errorLisB4Hsie + errorLisHts + errorNotOkLis);
			// 5. error count at HIPS
			int errorLisHips = rhapsodyDAO.errorCountLisAtHips(from, to, hospitalIds);
			summary.setLisErrorCntAtHips(errorLisHips);
		}
		
		// Radiology Info
		// 1. AIE to HTS
		int risCntAie2Hsie = rhapsodyDAO.totalCountRisAie2Hts(from, to, facility);
		summary.setRisCntAie2Hts(risCntAie2Hsie);
		if(risCntAie2Hsie > 0) {
			// 2. HTS to MHR
			int risCntHts2Mhr = rhapsodyDAO.totalCountRisHts2Mhr(from, to, hospitalIds);
			summary.setRisCntHts2Mhr(risCntHts2Mhr);
			// 3. success count from HIPS to MHR
			int successRisCntUpload = rhapsodyDAO.successCountRisUpload(from, to, hospitalIds);
			summary.setSuccessRisCntUpload(successRisCntUpload);
			// 4-1. error count before HSIE - Ack is not AA
			int errorRisB4Hsie = rhapsodyDAO.errorCountRisB4Hsie(from, to, facility);
			// 4-2. error count at HSIE
			int errorRisHts = rhapsodyDAO.errorCountRisAtHts(from, to, facility);
			// 4-3. IHI lookup failed- not included in PcehrAudit table
			int errorNotOkRis = rhapsodyDAO.notOkCountRisUpload(from, to, facility);
			summary.setRisErrorCntAtHts(errorRisB4Hsie + errorRisHts + errorNotOkRis);
			// 5. error count at HIPS
			int errorRisHips = rhapsodyDAO.errorCountRisAtHips(from, to, hospitalIds);
			summary.setRisErrorCntAtHips(errorRisHips);
		}
				
		// PSML Info
		// 1. AIE to HTS
		int psmlCntAie2Hsie = rhapsodyDAO.totalCountPsmlAie2Hts(from, to, facility);
		summary.setPsmlCntAie2Hts(psmlCntAie2Hsie);
		if(psmlCntAie2Hsie > 0) {
			// 2. HTS to MHR
			int psmlCntHts2Mhr = rhapsodyDAO.totalCountPsmlHts2Mhr(from, to, hospitalIds);
			summary.setPsmlCntHts2Mhr(psmlCntHts2Mhr);
			// 3. success count from HIPS to MHR
			int successPsmlCntUpload = rhapsodyDAO.successCountPsmlUpload(from, to, hospitalIds);
			summary.setSuccessPsmlCntUpload(successPsmlCntUpload);
			// 4-1. error count before HSIE - Ack is not AA
			int errorPsmlB4Hsie = rhapsodyDAO.errorCountPsmlB4Hsie(from, to, facility);
			// 4-2. error count at HTS
			int errorPsmlHts = rhapsodyDAO.errorCountPsmlAtHts(from, to, facility);
			summary.setPsmlErrorCntAtHts(errorPsmlB4Hsie + errorPsmlHts);
			// 5. error count at HIPS
			int errorPsmlHips = rhapsodyDAO.errorCountPsmlAtHips(from, to, hospitalIds);
			summary.setPsmlErrorCntAtHips(errorPsmlHips);
		}
	
		// Specialist Letter Info
		// 1. AIE to HTS
		int shsCntAie2Hsie = rhapsodyDAO.totalCountShsAie2Hts(from, to, facility);
		summary.setShsCntAie2Hts(shsCntAie2Hsie);
		if(shsCntAie2Hsie > 0) {
			// 2. HTS to MHR
			int shsCntHts2Mhr = rhapsodyDAO.totalCountShsHts2Mhr(from, to, hospitalIds);
			summary.setShsCntHts2Mhr(shsCntHts2Mhr);
			// 3. success count from HIPS to MHR
			int successShsCntUpload = rhapsodyDAO.successCountShsUpload(from, to, hospitalIds);
			summary.setSuccessShsCntUpload(successShsCntUpload);
			// 4-1. error count before HSIE - Ack is not AA
			int errorShsB4Hsie = rhapsodyDAO.errorCountShsB4Hsie(from, to, facility);
			// 4-2. error count at HTS
			int errorShsHts = rhapsodyDAO.errorCountShsAtHts(from, to, facility);
			summary.setShsErrorCntAtHts(errorShsB4Hsie + errorShsHts);
			// 5. error count at HIPS
			int errorShsHips = rhapsodyDAO.errorCountShsAtHips(from, to, hospitalIds);
			summary.setShsErrorCntAtHips(errorShsHips);
		}
	
		// Event Summary Info
		// 1. AIE to HTS
		int esCntAie2Hsie = rhapsodyDAO.totalCountEsAie2Hts(from, to, facility);
		summary.setEsCntAie2Hts(esCntAie2Hsie);
		if(esCntAie2Hsie > 0) {
			// 2. HTS to MHR
			int esCntHts2Mhr = rhapsodyDAO.totalCountEsHts2Mhr(from, to, hospitalIds);
			summary.setEsCntHts2Mhr(esCntHts2Mhr);
			// 3. success count from HIPS to MHR
			int successEsCntUpload = rhapsodyDAO.successCountEsUpload(from, to, hospitalIds);
			summary.setSuccessEsCntUpload(successEsCntUpload);
			// 4-1. error count before HSIE - Ack is not AA
			int errorEsB4Hsie = rhapsodyDAO.errorCountEsB4Hsie(from, to, facility);
			// 4-2. error count at HTS
			int errorEsHts = rhapsodyDAO.errorCountEsAtHts(from, to, facility);
			summary.setEsErrorCntAtHts(errorEsB4Hsie + errorEsHts);
			// 5. error count at HIPS
			int errorEsHips = rhapsodyDAO.errorCountEsAtHips(from, to, hospitalIds);
			summary.setEsErrorCntAtHips(errorEsHips);
		}
		return summary;
	}

	@Override
	@Transactional
	public List<ExceptionListData> getExceptionList(ExceptionBasicData data, String[] types) {
		String from = data.getFromDate();
		String to = data.getToDate();
		from += ExceptionReportConstants.FROM_DATE_SUFFIX;
		to += ExceptionReportConstants.TO_DATE_SUFFIX;
		String facility = data.getFacility();
		// get hospitalID by facility code
		List<String> hospitalIds = getHospitalIds(facility);
		// get document Id
		List<Integer> documentIds = getDocumentIds(types);
		// get document name
		List<String> documentNames = getDocumentNames(types);
		List<ExceptionListData> b4Hsie = rhapsodyDAO.listExceptionAtBeginOfHsie(from, to, facility, documentNames);
		b4Hsie = cleanAie2HtsList(b4Hsie);
		List<ExceptionListData> aie2Hts = rhapsodyDAO.listExceptionAtHts(from, to, facility, documentNames);
		aie2Hts = cleanAie2HtsList(aie2Hts);
		List<ExceptionListData> hts2Mhr = rhapsodyDAO.listExceptionAtMhr(from, to, hospitalIds, documentIds);
		hts2Mhr = cleanHts2MhrList(hts2Mhr);
		List<ExceptionListData> notOk = rhapsodyDAO.listNotOkHts2Mhr(from, to, facility, documentNames);
		notOk = cleanHts2MhrList(notOk);
		
		// concatenate all list
		List<ExceptionListData> lists = Stream.of(b4Hsie, aie2Hts, hts2Mhr, notOk).flatMap(Collection::stream).collect(Collectors.toList());
		return lists;
	}
	
	
	// to keep consistent result context
	private List<ExceptionListData> cleanAie2HtsList(List<ExceptionListData> exceptions){
		if(exceptions!=null && exceptions.size()>0) {
			for(ExceptionListData exception : exceptions) {
				// clean up address
//				String address = exception.getAddress();
//				if(StringUtils.containsAny(address, "^")) {
//					String updatedAddress = StringUtils.replaceChars(address, '^', ' ');
//					exception.setAddress(updatedAddress);
//				}
				
				// check gender
				String gender = exception.getGender();
				if(StringUtils.equalsIgnoreCase(gender, "F")) {
					exception.setGender("2");
				}else if(StringUtils.equalsIgnoreCase(gender, "M")) {
					exception.setGender("1");
				}
				
				// document type
				String type = exception.getDocumentType();
				if(StringUtils.startsWithIgnoreCase(type, ExceptionReportConstants.DISCHARGE_SUMMARY)) {
					exception.setDocumentType(ExceptionReportConstants.DISCHARGE_SUMMARY);
				}
				
				// clean up detail
				// remove audit_id in exception detail
				String detail = exception.getException();
				String id = "[" + exception.getId() + "]";
				if(StringUtils.containsAny(detail, id)){
					String updated = StringUtils.remove(detail, id);
					updated = StringUtils.removeEndIgnoreCase(updated, ExceptionReportConstants.ERROR_SUFFIX) + ".";   //replace(updated, delete, ".");
					exception.setException(updated);
				}				
				String[] prefixs = ExceptionReportConstants.ERROR_PREFIX;
				for(String prefix: prefixs) {
					if(StringUtils.startsWithIgnoreCase(detail, prefix)){
						String updated = StringUtils.removeStartIgnoreCase(detail, prefix);
						exception.setException(updated);
					}
				}
			}
		}
		return exceptions;
		
	}
	
	// remove prefix words at exception detail
	private List<ExceptionListData> cleanHts2MhrList(List<ExceptionListData> exceptions){
		if(exceptions!=null && exceptions.size()>0) {
			for(ExceptionListData exception : exceptions) {
				
				// clean up address
//				String address = exception.getAddress();
//				if(StringUtils.containsAny(address, "^")) {
//					String updatedAddress = StringUtils.replaceChars(address, '^', ' ');
//					exception.setAddress(updatedAddress);
//				}
				
				// check gender
				String gender = exception.getGender();
				if(StringUtils.equalsIgnoreCase(gender, "F")) {
					exception.setGender("2");
				}else if(StringUtils.equalsIgnoreCase(gender, "M")) {
					exception.setGender("1");
				}
				
				// document type
				String type = exception.getDocumentType();
				if(StringUtils.startsWithIgnoreCase(type, ExceptionReportConstants.DISCHARGE_SUMMARY)) {
					exception.setDocumentType(ExceptionReportConstants.DISCHARGE_SUMMARY);
				}
				
				// clean up detail
				String detail = exception.getException();
				String[] prefixs = ExceptionReportConstants.ERROR_PREFIX;
				for(String prefix: prefixs) {
					if(StringUtils.startsWithIgnoreCase(detail, prefix)){
						String updated = StringUtils.removeStartIgnoreCase(detail, prefix);
						updated = StringUtils.removeEndIgnoreCase(updated, ExceptionReportConstants.ERROR_SUFFIX) + ".";  
						exception.setException(updated);
					}
				}
				
//				// set fromWhere to HTS2MHR
//				exception.setFromWhere(ExceptionReportConstants.HTS2MHR);
			}
		}
		return exceptions;
	}
	
	@Override
	@Transactional
	public List<ExceptionAuditData> getAuditList(ExceptionBasicData data) {
		String from = data.getFromDate();
		String to = data.getToDate();
		from += ExceptionReportConstants.FROM_DATE_SUFFIX;
		to += ExceptionReportConstants.TO_DATE_SUFFIX;
		String facility = data.getFacility();
		String patient = data.getPatientInfo();
		List<ExceptionAuditData> audits = rhapsodyDAO.listAudit(patient, from, to, facility);
		audits = cleanAuditList(audits);
		return audits;
		//return rhapsodyDAO.listAudit(patient, from, to, facility);
	}
	
	// to keep consistent result context
	private List<ExceptionAuditData> cleanAuditList(List<ExceptionAuditData> audits){
		if(audits!=null && audits.size()>0) {
			for(ExceptionAuditData audit : audits) {
				String serviceName = ExceptionReportUtils.splitCamelCase(audit.getServiceName());
				audit.setServiceName(serviceName);
			}
		}
		return audits;
	}
	
	// convert facility codes to hospital IDs
	private List<String> getHospitalIds(String facility) {
		List<String> ids = new ArrayList<String>();
		if(StringUtils.contains(facility, ",")) {
			String[] sites = StringUtils.split(facility, ",");
			for(String site : sites) {
				String fac = configDAO.getHospitalId(site)+"";
				ids.add(fac);
			}
		}else {
			String fac = configDAO.getHospitalId(facility)+"";
			ids.add(fac);
		}
		return ids;
	}

	// convert document type to document IDs
	private List<Integer> getDocumentIds(String[] types) {
		List<Integer> ids = new ArrayList<Integer>();
		for(String type : types) {
			switch(type) {
				case ExceptionReportConstants.TYPE_DISCHARGE_SUMMARY:
					ids.add(1);
					break;
				case ExceptionReportConstants.TYPE_PATHOLOGY:
					ids.add(7);
					break;
				case ExceptionReportConstants.TYPE_RADIOLOGY:
					ids.add(8);
					break;
				case ExceptionReportConstants.TYPE_PHARMACIST_SHARED_MEDICINES_LIST:
					ids.add(18);
					break;
				case ExceptionReportConstants.TYPE_SPECIALIST_LETTER:
					ids.add(5);
					break;
				case ExceptionReportConstants.TYPE_EVENT_SUMMARY:
					ids.add(4);
			}
		}
		return ids;
	}

	// convert document type to document names
	private List<String> getDocumentNames(String[] types) {
		List<String> names = new ArrayList<String>();
		for(String type : types) {
			switch(type) {
				case ExceptionReportConstants.TYPE_DISCHARGE_SUMMARY:
					names.add(ExceptionReportConstants.DISCHARGE_SUMMARY_1A);
					names.add(ExceptionReportConstants.DISCHARGE_SUMMARY_3A);
					break;
				case ExceptionReportConstants.TYPE_PATHOLOGY:
					names.add(ExceptionReportConstants.PATHOLOGY_REPORT);
					break;
				case ExceptionReportConstants.TYPE_RADIOLOGY:
					names.add(ExceptionReportConstants.RADIOLOGY_REPORT);
					break;
				case ExceptionReportConstants.TYPE_PHARMACIST_SHARED_MEDICINES_LIST:
					names.add(ExceptionReportConstants.PSML_REPORT);
					break;
				case ExceptionReportConstants.TYPE_SPECIALIST_LETTER:
					names.add(ExceptionReportConstants.SPECIALIST_LETTER_REPORT);
					break;
				case ExceptionReportConstants.TYPE_EVENT_SUMMARY:
					names.add(ExceptionReportConstants.EVENT_SUMMARY_REPORT);
			}
		}
		return names;
	}
	

	@Override
	@Transactional
	public List<ExceptionSimpleData> getSiteList(String name) {
		return configDAO.getSiteList(name);
	}

	@Override
	@Transactional
	public List<ExceptionSimpleData> getSiteAllList() {
		return configDAO.getSiteAllList();
	}

	@Override
	@Transactional
	public List<ExceptionSimpleData> getAgencyList() {
		return configDAO.getAgencyList();
	}
	
}
