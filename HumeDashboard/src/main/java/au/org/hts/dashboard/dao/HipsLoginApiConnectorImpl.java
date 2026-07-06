package au.org.hts.dashboard.dao;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import au.org.hts.dashboard.entity.ApiResponse;
import au.org.hts.dashboard.entity.UserData;
import au.org.hts.dashboard.util.HumeDashboardConstants;

@Repository
public class HipsLoginApiConnectorImpl implements HipsLoginApiConnector {
	
	private final RestTemplate restTemplate;
	
	@Value("${login.api.endpoint}")
	private String endPoint;
	
	@Value("${authorised.agency.list}")
	private String hospitalList;
	
	public HipsLoginApiConnectorImpl(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}
	
	// check if agency belongs to hospitalList or not
	public boolean isAuthorisedGroup(String groupName) {
	    return Arrays.stream(hospitalList.split("\\s*,\\s*")) // trim and comma seprate
	                 .anyMatch(hospital -> StringUtils.equalsIgnoreCase(hospital, groupName));	
	}
	
	@Override
	public UserData authenticate(String username, String password) throws BadCredentialsException {
		String url = endPoint + "?username={username}&password={password}";
		UserData user = null;
        try {
        	// Step 1. Authentication via Login API
        	ParameterizedTypeReference<ApiResponse<UserData>> typeRef = new ParameterizedTypeReference<ApiResponse<UserData>>() {};
        	ResponseEntity<ApiResponse<UserData>> response = restTemplate.exchange(url, HttpMethod.GET, null, typeRef, username, password);
//        	System.out.println("Raw response: " + response.getBody());
        	String status = response.getBody().getStatus();
        	if(StringUtils.equalsIgnoreCase(status, HumeDashboardConstants.ERROR_RESPONSE)) {
        		String errorMsg = response.getBody().getMessage(); // Invalid username, Invalid password
        		throw new BadCredentialsException(errorMsg);
        	}
        	// Step 2. Authorisation via application.properties
	    	user = response.getBody().getData();	    
        	String role = user.getRole();
        	role = StringUtils.removeStartIgnoreCase(role, HumeDashboardConstants.ROLE_PREFIX);
	    	if(!isAuthorisedGroup(role)) {
	    		throw new BadCredentialsException("unauthorised");
	    	}
			return user;
        }catch(RestClientException ex) {
        	//ex.printStackTrace();
			throw new BadCredentialsException("login_api_error", ex);
        }        
	}
		
}
