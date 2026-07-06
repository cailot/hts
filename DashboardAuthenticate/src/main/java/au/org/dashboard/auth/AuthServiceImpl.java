package au.org.dashboard.auth;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import au.org.dashboard.auth.dao.AuthUserAccountDAO;
import au.org.dashboard.auth.entity.UserData;
import au.org.dashboard.auth.security.Encryption;
import au.org.dashboard.auth.util.ResponseUtil;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthUserAccountDAO authUserAccountDAO;
	@Autowired
	private Encryption encoder;

	@Override
	public UserData getUser(String username, String password) {
		// create error object
		// 99 = invalid user
		// 96 = invalid password
		// 98 = missing parameters
		// 97 = unknown error
		UserData user = ResponseUtil.getEmptyUser(99);
		
		try {		
			
			UserData userCheck = null;
			try {
				userCheck = authUserAccountDAO.findUserByUsername(username);
			}catch (NoResultException nre) {
				//ignore error
			}catch (EmptyResultDataAccessException erdae) {
				//ignore error
			}
			
			if(userCheck == null) {
				//System.out.println("No records found for username");
				user.setEnabled(99);
				return user;				
			}
			else
			{
				//System.out.println(userCheck.toString()); 
				
				Boolean equal = encoder.passwordEncoder().matches(password, userCheck.getPassword());
				if (equal) {  // success 
					//System.out.println("Are passwords equal? true");
					user = userCheck;
					// update login timestamp
					authUserAccountDAO.loginSuccess(username);
					// hide password from response
					user.setPassword("*****");
					return user;
				}else {  // fail
					//System.out.println("Are passwords equal? false");
					// update failure count++
					authUserAccountDAO.loginFail(username);
			        user.setEnabled(96);
					return user;
				}
			}			
		}catch (Exception e) {
			System.out.println("Exception: "+e);
			user.setEnabled(97);
			return user;
		}
	}
}