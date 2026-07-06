package au.org.dashboard.auth.dao;

import au.org.dashboard.auth.entity.UserData;

public interface AuthUserAccountDAO {

	// find user
	UserData findUserByUsername(String username);

	// success login so update login timestamp
	int loginSuccess(String username);
	
	// login failure so increase failure count
	int loginFail(String username);
	
}
