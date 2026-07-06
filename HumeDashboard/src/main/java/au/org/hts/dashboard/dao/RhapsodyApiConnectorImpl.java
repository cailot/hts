package au.org.hts.dashboard.dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import au.org.hts.dashboard.util.HumeDashboardConstants;


@Repository
public class RhapsodyApiConnectorImpl implements RhapsodyApiConnector {
	

	private static final Logger logger = LogManager.getLogger(RhapsodyApiConnectorImpl.class);
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	@Value("${rest.api.endpoint}")
	private String endPoint;

	@Value("${rest.api.username}")
	private String apiUsername;
	
	@Value("${rest.api.password}")
	private String apiPassword;

	@Value("${rest.alert.active.state}")
	private String alertActiveState;

	@Value("${rest.alert.default.setting}")
	private String alertDefaultSetting;

	@Value("${rest.alert.setting}")
	private String alertSetting;

	@Value("${rest.alert.delivery.method}")
	private String alertDeliveryMethod;

	@Value("${rest.archive.cleanup}")
	private String archiveCleanup;

	@Value("${rest.archive.cleanup.setting}")
	private String archiveCleanupSetting;

	@Value("${rest.archive.cleanup.defrag}")
	private String archiveDefrag;

	@Value("${rest.archive.cleanup.defrag.setting}")
	private String archiveDefragSetting;

	@Value("${rest.backup.schedule}")
	private String backupSchedule;

	@Value("${rest.basic.info}")
	private String basicInfo;

	@Value("${rest.component.info}")
	private String componentInfo;

	@Value("${rest.component.status}")
	private String componentStatus;

	@Value("${rest.component.registeredport}")
	private String registeredPorts;

	@Value("${rest.component.commpoint}")
	private String commpoint;

	@Value("${rest.component.route}")
	private String route;

	@Value("${rest.component.webservice}")
	private String webservice;

	@Value("${rest.component.watchlist}")
	private String watchlist;

	@Value("${rest.statistics.memory}")
	private String memoryUsage;

	@Value("${rest.statistics.disk}")
	private String diskSpace;

	@Value("${rest.statistics.cpu}")
	private String cpuUsage;

	@Value("${rest.statistics.message}")
	private String messageCount;

	@Value("${rest.errorqueue.count}")
	private String errorqueue;

	@Value("${rest.holdqueue.count}")
	private String holdqueue;

	@Value("${rest.message.retrieval}")
	private String messageRetrieval;

	@Value("${rest.admin.config}")
	private String adminConfig;

	@Value("${rest.admin.module}")
	private String adminModule;

	@Value("${rest.admin.library}")
	private String adminLibrary;

	@Value("${rest.admin.license}")
	private String adminLicense;

	@Value("${rest.admin.lookuptable}")
	private String adminLookuptable;

	@Value("${rest.admin.security}")
	private String adminSecurity;

	@Value("${rest.admin.user}")
	private String adminUser;

	@Value("${rest.admin.group}")
	private String adminGroup;

	@Value("${rest.admin.variable}")
	private String adminVariable;

	@Value("${rest.admin.webservice.user}")
	private String adminWebUser;

	// authentication cookie
	private String cookie;
	
	private String token;

	@Override
	public String getActiveAlert(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + alertActiveState);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getActiveAlert(String engine, String id) {
		String response = null;
		String uri = alertActiveState;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/state");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getAlertDefaultSetting(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + alertDefaultSetting);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getAlertSetting(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + alertSetting);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getAlertDeliveryMethods(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + alertDeliveryMethod);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getArchiveCleanup(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + archiveCleanup);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getArchiveCleanupSetting(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + archiveCleanupSetting);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getArchiveDefrag(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + archiveDefrag);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getArchiveDefragSetting(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + archiveDefragSetting);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getBackupSchedule(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + backupSchedule);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getBackupSchedule(String engine, String id) {
		String response = null;
		String uri = backupSchedule;
		try {
			response = inovkeGet(engine + uri + "/" + id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	
	@Override
	public String getBasicInfo() {
		String response = null;
		try {
			response = inovkeGet(endPoint + basicInfo);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getBasicInfo(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + basicInfo);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public String getTotalMessageCount() {
		String response = null;
		try {
			response = inovkeGet(endPoint + messageCount);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getTotalMessageCount(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + messageCount);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public String getComponents(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + componentInfo);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getComponentsStatus() {
		String response = null;
		try {
			response = inovkeGet(endPoint + componentStatus);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public String getComponentsStatus(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + componentStatus);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getRegisteredPorts(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + registeredPorts);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCommpoint(String engine, String id) {
		String response = null;
		String uri = commpoint;
		try {
			response = inovkeGet(engine + uri + "/" + id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public String getCommpoint(String id) {
		String response = null;
		String uri = commpoint;
		try {
			response = inovkeGet(endPoint + uri + "/" + id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCommpointState(String engine, String id) {
		String response = null;
		String uri = commpoint;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/state");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCommpointMessageCount(String engine, String id) {
		String response = null;
		String uri = commpoint;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/messagecount");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCommpointSupportNotes(String engine, String id) {
		String response = null;
		String uri = commpoint;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/supportnotes");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCommpointAlertSettings(String engine, String id) {
		String response = null;
		String uri = commpoint;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/alerts/customsettings");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getRoute(String engine, String id) {
		String response = null;
		String uri = route;
		try {
			response = inovkeGet(engine + uri + "/" + id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getRouteState(String engine, String id) {
		String response = null;
		String uri = route;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/state");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getRouteMessageCount(String engine, String id) {
		String response = null;
		String uri = route;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/messagecount");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getRouteSupportNotes(String engine, String id) {
		String response = null;
		String uri = route;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/supportnotes");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getFilterSupportNotes(String engine, String id) {
		String response = null;
		String uri = route;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/filter/" + id + "/supportnotes");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getRouteAlertSettings(String engine, String id) {
		String response = null;
		String uri = route;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/alerts/customsettings");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getWebservice(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + webservice);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getWebservice(String engine, String id) {
		String response = null;
		String uri = webservice;
		try {
			response = inovkeGet(engine + uri + "/" + id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getWebserviceState(String engine, String id) {
		String response = null;
		String uri = webservice;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/state");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getWebserviceMessageCount(String engine, String id) {
		String response = null;
		String uri = webservice;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/messagecount");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getWebserviceAlertSettings(String engine, String id) {
		String response = null;
		String uri = webservice;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/alerts/customsettings");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getWatchlist(String engine, String name) {
		String response = null;
		String uri = watchlist;
		try {
			response = inovkeGet(engine + uri + "/" + name + "/alerts/active");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}


	@Override
	public String getMemoryUsage(String engine) {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		Date before = DateUtils.addMinutes(now, -30); // 30 min before
		try {
			String body = "{\"memoryType\":\"WORKING\",\"startTime\":\""+ simpleDateFormat.format(before) +"\",\"endTime\":\""+simpleDateFormat.format(now) +"\",\"samplingResolution\":\"PT1M\"}";
			sb.append(invokePost(engine + memoryUsage, body));
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	@Override
	public String getMemoryUsage() {
		StringBuilder sb = new StringBuilder();
		Date now = new Date();
		Date before = DateUtils.addMinutes(now, -30); // 30 min before
		try {
			String body = "{\"memoryType\":\"WORKING\",\"startTime\":\""+ simpleDateFormat.format(before) +"\",\"endTime\":\""+simpleDateFormat.format(now) +"\",\"samplingResolution\":\"PT1M\"}";
			sb.append(invokePost(endPoint + memoryUsage, body));
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException | AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	@Override
	public String getSimpleMemoryUsage(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + memoryUsage);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public String getSimpleMemoryUsage() {
		String response = null;
		try {
			response = inovkeGet(endPoint + memoryUsage);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getDiskspace() {
		String response = null;
		try {
			response = inovkeGet(endPoint + diskSpace);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public String getDiskspace(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + diskSpace);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCPUUsage() {
		String response = null;
		try {
			response = inovkeGet(endPoint + cpuUsage);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	@Override
	public String getCPUUsage(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + cpuUsage);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getErrorqueueCount(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + errorqueue);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getHoldqueueCount(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + holdqueue);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getMessageMeta(String engine, String id) {
		String response = null;
		String uri = messageRetrieval;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/meta");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getMessagePath(String engine, String id) {
		String response = null;
		String uri = messageRetrieval;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/path");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getMessageBody(String engine, String id) {
		String response = null;
		String uri = messageRetrieval;
		try {
			response = inovkeGet(engine + uri + "/" + id + "/body");
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getMessage(String engine, String id) {
		String response = null;
		String uri = messageRetrieval;
		try {
			response = inovkeGet(engine + uri + "/" + id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getConfigStatus(String engine, String id) {
		String response = null;
		String uri = adminConfig;
		try {
			response = inovkeGet(engine + uri + "/" + id);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCustomModule(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminModule);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCustomModule(String engine, String name) {
		String response = null;
		String uri = adminModule;
		try {
			response = inovkeGet(engine + uri + "/" + name);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCustomLibrary(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminLibrary);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getCustomLibrary(String engine, String name) {
		String response = null;
		String uri = adminLibrary;
		try {
			response = inovkeGet(engine + uri + "/" + name);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getLicense(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminLicense);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getLookuptable(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminLookuptable);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getSecurity(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminSecurity);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getUser(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminUser);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getGroup(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminGroup);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getVariable(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminVariable);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getVariable(String engine, String name) {
		String response = null;
		String uri = adminVariable;
		try {
			response = inovkeGet(engine + uri + "/" + name);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getWebserviceUser(String engine) {
		String response = null;
		try {
			response = inovkeGet(engine + adminWebUser);
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	private String inovkeGet(String endpoint) throws KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, ClientProtocolException, IOException {
		SSLContextBuilder builder = new SSLContextBuilder();

		String raw = null;
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(),
				NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory()).register("https", sslConnectionSocketFactory)
				.build();

		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		cm.setMaxTotal(100);

		CookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory)
				.setConnectionManager(cm).setDefaultCookieStore(cookieStore).build();

		// add JSESSIONID in the Header to avoid basic authentication
		HttpGet reqGet = new HttpGet(endpoint);
		reqGet.setHeader(HumeDashboardConstants.COOKIE, cookie);
//		reqGet.setHeader(DashboardConstants.ACCEPT, "application/json");
		
		
		Header[] heads = reqGet.getAllHeaders();
		for(int i=0; i<heads.length; i++) {
			logger.trace(heads[i]);
		}
		

		CloseableHttpResponse response = httpclient.execute(reqGet);

		int resStatusCode = response.getStatusLine().getStatusCode();
		if (resStatusCode == 200 || resStatusCode == 201) {
			Header[] headers = response.getAllHeaders();
			for (int i = 0; i < headers.length; i++) {
				logger.trace(" ==> " + headers[i]);
			}

			HttpEntity httpEntity = response.getEntity();
			String jsonOutput = EntityUtils.toString(httpEntity);
			raw = StringEscapeUtils.unescapeJava(jsonOutput);
			logger.trace("Reponse OK");// + jsonOutput);

		} else {
			logger.error("Commnication falied with the status Code: " + resStatusCode);
		}

		httpclient.close();
		response.close();
		return raw;
	}

	private String invokePost(String endpoint, String body) throws KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, ClientProtocolException, IOException, AuthenticationException {
		SSLContextBuilder builder = new SSLContextBuilder();

		String raw = null;
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(),
				NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", new PlainConnectionSocketFactory()).register("https", sslConnectionSocketFactory)
				.build();

		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
		cm.setMaxTotal(100);
		
		
		CookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory)
				.setConnectionManager(cm).setDefaultCookieStore(cookieStore).build();



		HttpPost reqPost = new HttpPost(endpoint);
		reqPost.setHeader(HumeDashboardConstants.COOKIE, cookie); 
		reqPost.setHeader(HumeDashboardConstants.CONTENT_TYPE, "application/json");
		reqPost.setHeader(HumeDashboardConstants.ACCEPT, "application/json");
		reqPost.setHeader(HumeDashboardConstants.AUTH_TOKEN, token);
		
		StringEntity entity = new StringEntity(body);
		reqPost.setEntity(entity);
		
		
		
		
		Header[] heads = reqPost.getAllHeaders();
		for(int i=0; i<heads.length; i++) {
			logger.trace(heads[i]);
		}
		

		CloseableHttpResponse response = httpclient.execute(reqPost);

		int resStatusCode = response.getStatusLine().getStatusCode();
		if (resStatusCode == 200 || resStatusCode == 201) {
			Header[] headers = response.getAllHeaders();
			for (int i = 0; i < headers.length; i++) {
//				System.out.println(" ==> " + headers[i]);
			}

			HttpEntity httpEntity = response.getEntity();
			String jsonOutput = EntityUtils.toString(httpEntity);
			raw = StringEscapeUtils.unescapeJava(jsonOutput);
			logger.trace("Reponse OK");// + jsonOutput);

		} else {
			logger.error("Commnication falied with the status Code: " + resStatusCode);
		}

		httpclient.close();
		response.close();
		return raw;
	}

	
	
	@Override
	public void setToken() {
		SSLContextBuilder builder = new SSLContextBuilder();
		try {
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());

			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(),
					NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", new PlainConnectionSocketFactory()).register("https", sslConnectionSocketFactory)
					.build();

			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(100);
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials defaultcreds = new UsernamePasswordCredentials(apiUsername, apiPassword);
			provider.setCredentials(AuthScope.ANY, defaultcreds);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory)
					.setConnectionManager(cm).setDefaultCredentialsProvider(provider).build();
			HttpGet reqGet = new HttpGet(endPoint + basicInfo);
			CloseableHttpResponse response = httpclient.execute(reqGet);
			int resStatusCode = response.getStatusLine().getStatusCode();
			if (resStatusCode == 200 || resStatusCode == 201) {
				Header[] headers = response.getAllHeaders();
				for (int i = 0; i < headers.length; i++) {
					// extract JSESSIONID
					if (StringUtils.containsIgnoreCase(headers[i].getName(), HumeDashboardConstants.COOKIE)) {
						String cooks = headers[i].getValue();
						String[] cookValue = StringUtils.split(cooks, ";");
						cookie = cookValue[0];
					}
					if (StringUtils.containsIgnoreCase(headers[i].getName(), HumeDashboardConstants.AUTH_TOKEN)) {
						String cooks = headers[i].getValue();
						token = cooks;
					}
				}
			} else {
				logger.error("Commnication falied with the status Code: " + resStatusCode);
			}

			httpclient.close();
			response.close();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void setToken(String engine) {
		SSLContextBuilder builder = new SSLContextBuilder();
		try {
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());

			SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(builder.build(),
					NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", new PlainConnectionSocketFactory()).register("https", sslConnectionSocketFactory)
					.build();

			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(100);
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials defaultcreds = new UsernamePasswordCredentials(apiUsername, apiPassword);
			provider.setCredentials(AuthScope.ANY, defaultcreds);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory)
					.setConnectionManager(cm).setDefaultCredentialsProvider(provider).build();

			HttpGet reqGet = new HttpGet(engine + basicInfo);

			CloseableHttpResponse response = httpclient.execute(reqGet);

			int resStatusCode = response.getStatusLine().getStatusCode();
			if (resStatusCode == 200 || resStatusCode == 201) {
				Header[] headers = response.getAllHeaders();
				for (int i = 0; i < headers.length; i++) {
					// extract JSESSIONID
					if (StringUtils.containsIgnoreCase(headers[i].getName(), HumeDashboardConstants.COOKIE)) {
						String cooks = headers[i].getValue();
						String[] cookValue = StringUtils.split(cooks, ";");
						cookie = cookValue[0];
					}
					if (StringUtils.containsIgnoreCase(headers[i].getName(), HumeDashboardConstants.AUTH_TOKEN)) {
						String cooks = headers[i].getValue();
						token = cooks;
					}
				}
			} else {
				logger.error("Commnication falied with the status Code: " + resStatusCode);
			}

			httpclient.close();
			response.close();
		} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
