package au.org.dashboard.auth;

import au.org.dashboard.auth.entity.UserData;

public interface AuthService {
	
	UserData getUser(String username, String password);
	
}
