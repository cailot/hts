package au.org.hts.dashboard.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import au.org.hts.dashboard.dao.HipsLoginApiConnector;
import au.org.hts.dashboard.entity.UserData;

@Service
public class LoginAuthenticationProvider implements AuthenticationProvider {

	
	private final HipsLoginApiConnector loginApiConnector;
	
	public LoginAuthenticationProvider(HipsLoginApiConnector loginApiConnector) {
		this.loginApiConnector = loginApiConnector;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String rawPassword = authentication.getCredentials().toString();
		UserData user = loginApiConnector.authenticate(username, rawPassword);
		UserDetails details = user.toUserDetails();
		return new UsernamePasswordAuthenticationToken(details, rawPassword, details.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}