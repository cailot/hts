package au.org.htsv.hips.report.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.entity.UserData;

public interface UserAccountDAO {

	// list all users
	List<UserData> getAllUsers();

	// find user
	UserData findUserByUsername(String username);
	
	// add user
	int addUser(UserData user);
	
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
