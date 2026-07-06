package au.org.hts.dashboard.dao;

import org.springframework.security.authentication.BadCredentialsException;

import au.org.hts.dashboard.entity.UserData;

public interface HipsLoginApiConnector {

	UserData authenticate(String username, String password) throws BadCredentialsException;
		
}
