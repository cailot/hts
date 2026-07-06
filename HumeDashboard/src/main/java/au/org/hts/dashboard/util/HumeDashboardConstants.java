package au.org.hts.dashboard.util;import org.apache.commons.lang3.math.NumberUtils;

public interface HumeDashboardConstants {
	
	String HTS = "HTS";
	
	String ORGANISATIONS = "orgs";
	
	String ORGANISATION_NAME = "orgName";
	
	String HOSPITAL_LIST = "hospitalList";
	
	String WMC = "console";
	
	// Search parameters
	String SENDING_FAC = "sendingFac";
	
	String RECEIVING_FAC = "receivingFac";
	
	String ACRONYM = "acronym";
	
	String MSG_TYPE = "msgType";
	
	String MSG_EVENT = "msgEvent";
	
	String FROM_DATE_TIME = "fromDateTime";
	
	String TO_DATE_TIME = "toDateTime";
	
	String TOTAL_MSG_IN_CNT= "inTotalMsgCnt";
	
	String TOTAL_MSG_OUT_CNT = "outTotalMsgCnt";
	
	String LAST_INBOUND_UPDATE = "inLastUpdate";
	
	String LAST_OUTBOUND_UPDATE = "outLastUpdate";
	
	String DIRECTION_INBOUND = "Inbound";
	
	String DIRECTION_OUTBOUND = "Outbound";
	
	String RECEIVED = "RECEIVED";
	
	String SENT = "SENT";
	
	String TRANSACTION_LIST = "details";
	
	String HOSPITAL_ID = "hospitalId";
	
	String HOSPITAL_NAME = "hospitalName";
	
	String PORTFOLIO_ID = "portfolioId";
	
	String PORTFOLIO_NAME = "portfolioName";
	
	String ORGANISATION_ID = "organisationId";
	
	String ORGANISATION_DETAIL = "orgDetail";
	
	String FACILITY_CODE = "facilityCode";
	
	String COMMPOINT_NAME = "commpointName";
	
	String LOG_NAME ="hl7Log";
	
	String PORTFOLIO_PCMS = "1";
	
	String PORTFOLIO_CS = "2";
	
	String PORTFOLIO_CMS = "3";
	
	String PORTFOLIO_RHEMS = "4";
	
	String PORTFOLIO_FBI = "5";
	
	String PORTFOLIO_FMIS = "6";
	
	String PORTFOLIO_HIPS = "7";
	
	String PORTFOLIO_HSCDM = "8";
	
	String PORTFOLIO_PSML = "9";
	
	String PROJECT_CMS = "TRAK";
	
	String PROJECT_RHEMS = "RHEMS";

	String PROJECT_FBI = "FBI";

	String PROJECT_FMIS = "FMIS";
	
	String PROJECT_HIPS = "HIPS";
	
	String PROJECT_HSCDM = "HSCDM";
	
	String PROJECT_PSML = "PSML";
	
	// color codes
	String PRIMARY = "primary";
	
	String INFO = "info";
	
	String WARNING = "warning";
	
	String SUCCESS = "success";
	
	String DANGER = "danger";
	
	String SECONDARY = "secondary";
	
	String DARK = "dark";
	
	
	// Rhapsody REST API calls
	String AUTH_TOKEN = "X-CSRF-Token";
	
	String COOKIE = "Cookie";
	
	String CONTENT_TYPE = "Content-Type";
	
	String ACCEPT = "Accept";
	
	String NOTHING = "Not Updated Properly";
	
	String ZERO = "0";
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	//																					 //
	//						Display Info												 //
	//																					 //
	///////////////////////////////////////////////////////////////////////////////////////
	
	String ENGINE_INFO = "engineInfo";
	
	String TOTAL_MESSAGE_COUNT = "totalMsgCnt";
	
	String CPU_INFO = "cpuInfo";
	
	String MEMORY_INFO = "memoryInfo";
	
	String LOG_LIST = "logList";
	
	String COMMPOINT_LIST = "commpointList";
	
	String COMMPOINT_INBOUND_LIST = "inboundList";
	
	String COMMPOINT_OUTBOUND_LIST = "outboundList";
	
	String ORGANISATION_LIST = "organisationList";
	
	String ENGINE_HEALTH = "engineHealth";
	
	String JSON_DATA = "data";
	
	String ENGINE_VERSION = "version";
	
	String ENGINE_NAME = "name";
	
	String ENGINE_UPTIME = "uptime";
	
	String ENGINE_AVAILABLE_DISK = "availableDataSpace";
	
	String ENGINE_TOTAL_DISK = "totalDataSpace";
	
	String ENGINE_AVAILABLE_MEMORY = "inUse";
	
	String ENGINE_TOTAL_MEMORY = "totalAllocated";

	
	// error handling
	String STATUS_CODE = "statusCode";
	
	String ERROR_STATUS = "javax.servlet.error.status_code";
	
	String EXCEPTION = "javax.servlet.error.exception";
	
	String EXCEPTION_TYPE = "javax.servlet.error.exception_type";
	
	String ERROR_MESSAGE = "javax.servlet.error.message";
	
	String ERROR_URI = "javax.servlet.error.request_uri";
	
	String SERVLET_NAME = "javax.servlet.error.servlet_name";


	
	
	
///////////////////////////////////////////////////////////////////////////////////////
//																					 //
//						Components													 //
//																					 //
///////////////////////////////////////////////////////////////////////////////////////



String INPUT = "Input";

String OUTPUT = "Output";

String PROCESSING = "Processing";

String COMMON = "Common";

String STATE_RUNNING = "RUNNING";

String STATE_STOPPED = "STOPPED";

String COMPONENT_DATA = "data";

String COMPONENT_CHILD_FOLDERS = "childFolders";

String COMPONENT_CHILD_COMPONENT = "childComponents";

String COMPONENT_ID = "id";

String COMPONENT_UUID = "uuid";

String COMPONENT_NAME = "name";

String COMPONENT_STATE = "state";

String COMPONENT_TYPE = "type";

String COMPOINT_MODE = "mode";

String COMPOINT_INBOUND = "IN_OUT";

String COMPOINT_OUTBOUND ="OUT_IN";

String COMPOINT_FOLDER_PATH = "folderPath";

String INBOUND_QUEUE_SIZE = "inQueueSize";

String OUTBOUND_QUEUE_SIZE = "outQueueSize";

String CONNECTION_COUNT = "connectionCount";

String COMPOINT_RECEIVE_COUNT = "receivedCount";

String COMPOINT_SENT_COUNT = "sentCount";

String INBOUND_IDLE_TIME = "inputIdleTime";

String OUTBOUND_IDLE_TIME = "outputIdleTime";

String UPTIME = "uptime";

String COMPOINT = "COMMUNICATION_POINT";

String COMPOINT_PREFIX = "CP.";

String COMPOINT_DR_PREFIX = "CP.dr_";

String COMPOINT_TCP_PREFIX = "CP.tcp_";

String COMPOINT_HTTP_PREFIX = "CP.http_";

String COMPOINT_DIRECTORY_PREFIX = "CP.dir_";

	
	
String HL7_LOG_SEPERATOR = "=================== Ack =======================";	

String ROLE_PREFIX = "ROLE_";

String SUCCESS_RESPONSE = "success";

String ERROR_RESPONSE = "error";

}
