package au.org.htsv.hips.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import au.org.htsv.hips.report.dao.UserAccountDAO;
import au.org.htsv.hips.report.entity.UserAccountDetails;
import au.org.htsv.hips.report.entity.UserData;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountDAO userDAO;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserData user = userDAO.findUserByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("User : " + username + " was not found in the database");
		}
		return new UserAccountDetails(user);
	}
	
	@Override
	@Transactional
	public List<UserData> getAllUsers() {
		return userDAO.getAllUsers();
	}
	
	@Override
	@Transactional
	public UserData getUser(String username) {
		return userDAO.findUserByUsername(username);
	}

	@Override
	@Transactional
	public int addUser(UserData user) {
		return userDAO.addUser(user);
	}

	@Override	
	@Transactional
	public int modifyUser(UserData user) {
		return userDAO.modifyUser(user);
	}

	@Override
	@Transactional
	public int suspendUser(String username) {
		return userDAO.suspendUser(username);
	}

	@Override
	@Transactional
	public int activateUser(String username) {
		return userDAO.activateUser(username);
	}

	@Override
	@Transactional
	public int deleteUser(String username) {
		return userDAO.deleteUser(username);
	}
	
	@Override
	@Transactional
	public int updatePassword(UserData user) {
		return userDAO.updatePassword(user);
	}


	@Override
	@Transactional
	public int loginSuccess(String username) {
		return userDAO.loginSuccess(username);
	}

	@Override
	@Transactional
	public int loginFail(String username) {
		return userDAO.loginFail(username);
	}
	
	

}
