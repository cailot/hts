package au.org.htsv.hips.report.util;

public interface ExceptionReportConstants {

	//////////////////////////////////////////////////////////////////
	//
	//	Constants for Sex
	//
	//////////////////////////////////////////////////////////////////
	String NOT_SPECIFIED = "-1";
	String MALE = "1";
	String FEMALE = "2";
	String INTERSEX = "3";
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	Constants for Document type
	//
	//////////////////////////////////////////////////////////////////
	String DISCHARGE_SUMMARY = "Discharge Summary";
	String PATHOLOGY = "Pathology Report";
	String RADIOLOGY = "Radiology Report";
	String PHARMACIST_SHARED_MEDICINES_LIST = "Pharmacist Shared Medicines List";
	String SPECIALIST_LETTER = "Specialist Letter";
	String EVENT_SUMMARY = "Event Summary";
	String SHARED_HEALTH_SUMMARY = "Shared Health Summary";
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	Constants for Request Parameters
	//
	//////////////////////////////////////////////////////////////////
	String CAMPUS_ID = "campusId";
	String FROM_DATE = "fromDate";
	String TO_DATE = "toDate";
	String DASHBOARD_FROM_DATE = "dashboardFromDate";
	String DASHBOARD_TO_DATE = "dashboardToDate";
	String SUMMARY_FROM_DATE = "summaryFromDate";
	String SUMMARY_TO_DATE = "summaryToDate";
	String DETAIL_FROM_DATE = "detailFromDate";
	String DETAIL_TO_DATE = "detailToDate";
	String AUDIT_FROM_DATE = "auditFromDate";
	String AUDIT_TO_DATE = "auditToDate";
	String HOSPITAL_ID = "hospitalId";
	String SITE_ID = "siteId";
	String FACILITY_CODE = "facCode";
	String DOCUMENT_NAME = "docName";
	String DOCUMENT_ID = "docId";
	String AUDIT_ID = "auditId";
	String PATIENT_ID = "patientId";
	String PATIENT_INFO = "patientInfo";
	String CORRELATION_ID = "correlationId";
	String CAMPUS_CODE = "campusCode";
	String HOSPITAL_ACRONYM = "hospitalAcronym";
	String AGENCY_NAME = "agencyName";
	String USER_NAME = "username";
	String PASSWORD = "password";
	String ENABLED = "enabled";
	String FIRST_NAME = "firstname";
	String LAST_NAME = "lastname";
	String ROLE = "role";
	String ADD = "Add";
	String EDIT = "Edit";
	String DELETE = "Delete";
	String SUSPEND = "Suspend";
	String ACTIVATE = "Activate";
	String AHPRA_NUMBER = "ahpranumber";
	String PROVIDER_NUMBER = "providernumber";
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	Constants for Internal Objects
	//
	//////////////////////////////////////////////////////////////////
	String 	GENDERS = "genders";	
	String 	HOSPITALS = "hospitals";
	String 	HOSPITAL = "hospital";
	String 	DASHBOARD_HOSPITAL = "dashboardHospital";
	String 	SUMMARY_HOSPITAL = "summaryHospital";
	String 	DETAIL_HOSPITAL = "detailHospital";
	String 	AUDIT_HOSPITAL = "auditHospital";
	String 	CAMPUSES = "campuses";
	String  SITES = "sites";
	String  ACRONYM = "acronyms";
	String  ERRORS = "errors";
	
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	Constants for Account Info
	//
	//////////////////////////////////////////////////////////////////
	String 	HIPS_UI_ADMIN = "-GG-HIPS_UI_Admins";	
	String 	HIPS_UI_ADMIN_ALTERNATIVE = "-GG-HIPS-UI-Admins";
	
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	Constants for User Group
	//
	//////////////////////////////////////////////////////////////////
	String NO_ACCESS = "NO_ACCESS";
	String ADMINISTRATOR = "Administrator";
	String VIEWER = "Viewer";
	String HTS = "Health Technology Solutions";
	String ROLE_PREFIX = "ROLE_";
	
	String AGENCY_VALUE_SUPER = "0";
	String AGENCY_VALUE_NO_ACCESS = "-1";
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	Time suffix
	//
	//////////////////////////////////////////////////////////////////
	String FROM_DATE_SUFFIX = " 00:00:00";
	String TO_DATE_SUFFIX = " 23:59:59";

	
	//////////////////////////////////////////////////////////////////
	//
	//	Value for Document type
	//
	//////////////////////////////////////////////////////////////////
	String TYPE_DISCHARGE_SUMMARY = "ds";
	String TYPE_PATHOLOGY = "lis";
	String TYPE_RADIOLOGY = "ris";
	String TYPE_PHARMACIST_SHARED_MEDICINES_LIST = "psml";
	String TYPE_SPECIALIST_LETTER = "shs";
	String TYPE_EVENT_SUMMARY = "es";
	String TYPE_SHARED_HEALTH_SUMMARY = "hs";
	
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	Conformence level for Document type
	//
	//////////////////////////////////////////////////////////////////
	String DISCHARGE_SUMMARY_1A = "Discharge Summary 1A";
	String DISCHARGE_SUMMARY_3A = "Discharge Summary 3A";
	String PATHOLOGY_REPORT = "Pathology";
	String RADIOLOGY_REPORT = "Radiology";
	String PSML_REPORT = "PSML 1A";
	String SPECIALIST_LETTER_REPORT = "Specialist Letter 1A";
	String EVENT_SUMMARY_REPORT = "Event Summary 3A";

	
	//////////////////////////////////////////////////////////////////
	//
	//	Result Data Set
	//
	//////////////////////////////////////////////////////////////////
	String DASHBOARD_DATA = "dashboardData";
	String SUMMARY_DATA = "summaryData";
	String DETAIL_DATA = "detailData";
	String AUDIT_DATA = "auditData";
	String USER_LIST = "userList";
	String USER_DATA = "userData";
	String HPII_DATA = "hpiiData";
	String HPII_LIST = "hpiiList";
	
	
	
	//////////////////////////////////////////////////////////////////
	//
	//	Clean Error Info by removing prefix
	//
	//////////////////////////////////////////////////////////////////
	String[] ERROR_PREFIX = {"Error:  Code::HL7MessageInfoException ; Message::", "Error: Message::", "Error: HipsErrorMessage::"};
	String ERROR_SUFFIX = " ; Type::SystemError";

	
	//////////////////////////////////////////////////////////////////
	//
	//	HPI-I 
	//
	//////////////////////////////////////////////////////////////////
	String HPI_I = "hpii";
	String HPII_STATUS = "hpiistatus";
	String FACILITY_SAMPLE = "1010";	
	String PAS_FACILITY_CODE = "pasFacCd";
	String OK = "OK";
	String SUCCESS = "success";
	String TOTAL = "total";
	
	
}
