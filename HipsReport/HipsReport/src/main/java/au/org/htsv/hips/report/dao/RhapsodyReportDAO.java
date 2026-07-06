package au.org.htsv.hips.report.dao;

import java.util.List;
import java.util.Map;

import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.entity.ExceptionAuditData;
import au.org.htsv.hips.report.entity.ExceptionListData;

public interface RhapsodyReportDAO {

	/////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	Dashboard count info	
	//
	/////////////////////////////////////////////////////////////////////////////////////////////
	int successCountDischargeUpload(String from, String to, List<String> hospitalIds);

	int successCountLisUpload(String from, String to, List<String> hospitalIds);

	int successCountRisUpload(String from, String to, List<String> hospitalIds);
	
	int successCountPsmlUpload(String from, String to, List<String> hospitalIds);

	int successCountShsUpload(String from, String to, List<String> hospitalIds);

	int successCountEsUpload(String from, String to, List<String> hospitalIds);


	/////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	Summary count info	
	//
	/////////////////////////////////////////////////////////////////////////////////////////////
	// 1. stats from AIE to HTS
	int totalCountDischargeAie2Hts(String from, String to, String facility);
	
	int totalCountLisAie2Hts(String from, String to, String facility);
	
	int totalCountRisAie2Hts(String from, String to, String facility);
	
	int totalCountPsmlAie2Hts(String from, String to, String facility);
	
	int totalCountShsAie2Hts(String from, String to, String facility);
	
	int totalCountEsAie2Hts(String from, String to, String facility);
	
	// 2. stats from HTS to MHR
	int totalCountDischargeHts2Mhr(String from, String to, List<String> hospitalIds);

	int totalCountLisHts2Mhr(String from, String to, List<String> hospitalIds);

	int totalCountRisHts2Mhr(String from, String to, List<String> hospitalIds);

	int totalCountPsmlHts2Mhr(String from, String to, List<String> hospitalIds);

	int totalCountShsHts2Mhr(String from, String to, List<String> hospitalIds);

	int totalCountEsHts2Mhr(String from, String to, List<String> hospitalIds);

	// 3. error count at begin of HSIE
	int errorCountDischargeB4Hsie(String from, String to, String facility);
	
	int errorCountLisB4Hsie(String from, String to, String facility);
	
	int errorCountRisB4Hsie(String from, String to, String facility);
	
	int errorCountPsmlB4Hsie(String from, String to, String facility);
	
	int errorCountShsB4Hsie(String from, String to, String facility);
	
	int errorCountEsB4Hsie(String from, String to, String facility);
	
	// 4. error count at HTS
	int errorCountDischargeAtHts(String from, String to, String facility);
	
	int errorCountLisAtHts(String from, String to, String facility);
	
	int errorCountRisAtHts(String from, String to, String facility);
	
	int errorCountPsmlAtHts(String from, String to, String facility);
	
	int errorCountShsAtHts(String from, String to, String facility);
	
	int errorCountEsAtHts(String from, String to, String facility);
		
	// 5. error count at HIPS
	int errorCountDischargeAtHips(String from, String to, List<String> hospitalIds);
	
	int errorCountLisAtHips(String from, String to, List<String> hospitalIds);
	
	int errorCountRisAtHips(String from, String to, List<String> hospitalIds);
		
	int errorCountPsmlAtHips(String from, String to, List<String> hospitalIds);
	
	int errorCountShsAtHips(String from, String to, List<String> hospitalIds);
	
	int errorCountEsAtHips(String from, String to, List<String> hospitalIds);
	
	// 6. it looks OK but NOT OK - only for LIS & RIS
	int notOkCountLisUpload(String from, String to, String facility);
	
	int notOkCountRisUpload(String from, String to, String facility);


	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	Detail exception info	
	//
	/////////////////////////////////////////////////////////////////////////////////////////////
	// 1. list exception detail at the begin of HSIE - Ack is AE or AR
	List<ExceptionListData> listExceptionAtBeginOfHsie(String from, String to, String facility, List<String> documentNames);
	// 2. list exception detail at HTS
	List<ExceptionListData> listExceptionAtHts(String from, String to, String facility, List<String> documentNames);
	// 3. list exception detail at MHR
	List<ExceptionListData> listExceptionAtMhr(String from, String to, List<String> hospitalIds, List<Integer> documentIds);
	// 4. list Not OK excepton from HIPS though response is OK, Only for LIS & RIS
	List<ExceptionListData> listNotOkHts2Mhr(String from, String to, String facility, List<String> documentNames);


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	List Discharge Summary Excepton List	
	//
	/////////////////////////////////////////////////////////////////////////////////////////////
	List<ExceptionListData> dsExceptionAie2Hts(String from, String to, String facility);
	List<ExceptionListData> dsExceptionHts2Mhr(String from, String to, String facility);
	List<ExceptionListData> dsNotOkHts2Mhr(String from, String to, String facility);
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	List Pathology Excepton List	
	//
	/////////////////////////////////////////////////////////////////////////////////////////////
	List<ExceptionListData> lisExceptionAie2Hts(String from, String to, String facility);
	List<ExceptionListData> lisExceptionHts2Mhr(String from, String to, String facility);
	List<ExceptionListData> lisNotOkHts2Mhr(String from, String to, String facility);

	/////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	List Radiology Excepton List	
	//
	/////////////////////////////////////////////////////////////////////////////////////////////
	List<ExceptionListData> risExceptionAie2Hts(String from, String to, String facility);
	List<ExceptionListData> risExceptionHts2Mhr(String from, String to, String facility);
	List<ExceptionListData> risNotOkHts2Mhr(String from, String to, String facility);

*/
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	Audit trasaction info	
	//
	/////////////////////////////////////////////////////////////////////////////////////////////
	List<ExceptionAuditData> listAudit(String patient, String from, String to, String facility);

	
	

	

}
