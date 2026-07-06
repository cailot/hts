package au.org.htsv.hips.report.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import au.org.htsv.hips.report.entity.UserData;

public interface UserAccountService extends UserDetailsService{

	// show all user list
	List<UserData> getAllUsers();
	
	// retrieve user
	UserData getUser(String username);

	// add user
	int addUser(UserData user);// throws SQLIntegrityConstraintViolationException;
	
	// modify user 
	int modifyUser(UserData user);
	
	// suspend user
	int suspendUser(String username);

	// activate user
	int activateUser(String username);

	// delete user
	int deleteUser(String username);

	// update password
	int updatePassword(UserData user);

	// login success
	int loginSuccess(String username);
	
	// login fail
	int loginFail(String username);

}
