package au.org.htsv.hips.report.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.entity.UserData;
import au.org.htsv.hips.report.util.ExceptionReportConstants;

@Repository
public class UserAccountDAOImpl implements UserAccountDAO {

	@Autowired
	private EntityManager entityManager;

	@Value("${sql.report.user.list}")
	private String userList;

	@Value("${sql.report.user.find}")
	private String userFind;
	
	@Value("${sql.report.user.add}")
	private String userAdd;
	
	@Value("${sql.report.user.modify}")
	private String userModify;
	
	@Value("${sql.report.login.success}")
	private String loginSuccess;
	
	@Value("${sql.report.login.fail}")
	private String loginFail;

	@Value("${sql.report.user.suspend}")
	private String userSuspend;
	
	@Value("${sql.report.user.activate}")
	private String userActivate;

	@Value("${sql.report.user.delete}")
	private String userDelete;
	
	@Value("${sql.report.password.update}")
	private String passwordUpdate;
	
	@Override
	public List<UserData> getAllUsers() {
		Query query = entityManager.createNativeQuery(userList);
		List<Object[]> results = query.getResultList();
		List<UserData> list = new ArrayList<UserData>(results.size());
		for (Object[] r : results) {
			UserData user = new UserData(r);
			list.add(user);
		}
		return list;
	}

	@Override
	public UserData findUserByUsername(String username) {
		Query query = entityManager.createNativeQuery(userFind);
		query.setParameter(ExceptionReportConstants.USER_NAME, username);
		// make sure only one record returned
		UserData user = new UserData((Object[]) query.setMaxResults(1).getSingleResult());
		return user;
	}

	@Override
	public int addUser(UserData user) {
		String username = user.getUsername();
		String password = user.getPassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		String role = user.getRole();
		int result = 0;
		try {
			result = entityManager.createNativeQuery(userAdd)
					.setParameter(ExceptionReportConstants.USER_NAME, username)
					.setParameter(ExceptionReportConstants.PASSWORD, encodedPassword)
					.setParameter(ExceptionReportConstants.FIRST_NAME, firstname)
					.setParameter(ExceptionReportConstants.LAST_NAME, lastname)
					.setParameter(ExceptionReportConstants.ROLE, role)
					.executeUpdate();
		}catch(Exception e) {
			//result = 0; remains as 0 unless successful traction
		}
		return result;
	}

	@Override
	public int loginSuccess(String username) {
		//String username = user.getUsername();
		int result = entityManager.createNativeQuery(loginSuccess)
		.setParameter(ExceptionReportConstants.USER_NAME, username)
		.executeUpdate();
		return result;
	}
	
	@Override
	public int loginFail(String username) {
		//String username = user.getUsername();
		int result = entityManager.createNativeQuery(loginFail)
		.setParameter(ExceptionReportConstants.USER_NAME, username)
		.executeUpdate();
		return result;
	}
	
	@Override
	public int modifyUser(UserData user) {
		String username = user.getUsername();
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		String role = user.getRole();
		int result = entityManager.createNativeQuery(userModify)
		.setParameter(ExceptionReportConstants.USER_NAME, username)
		.setParameter(ExceptionReportConstants.FIRST_NAME, firstname)
		.setParameter(ExceptionReportConstants.LAST_NAME, lastname)
		.setParameter(ExceptionReportConstants.ROLE, role)
		.executeUpdate();
		return result;
	}

	@Override
	public int suspendUser(String username) {
		int result = entityManager.createNativeQuery(userSuspend)
		.setParameter(ExceptionReportConstants.USER_NAME, username)
		.executeUpdate();
		return result;
	}
	
	@Override
	public int activateUser(String username) {
		int result = entityManager.createNativeQuery(userActivate)
		.setParameter(ExceptionReportConstants.USER_NAME, username)
		.executeUpdate();
		return result;
	}
	
	@Override
	public int deleteUser(String username) {
		int result = entityManager.createNativeQuery(userDelete)
		.setParameter(ExceptionReportConstants.USER_NAME, username)
		.executeUpdate();
		return result;
	}
	
	@Override
	public int updatePassword(UserData user) {
		String username = user.getUsername();
		String password = user.getPassword();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		int result = entityManager.createNativeQuery(passwordUpdate)
		.setParameter(ExceptionReportConstants.USER_NAME, username)
		.setParameter(ExceptionReportConstants.PASSWORD, encodedPassword)
		.executeUpdate();
		return result;
	}
}
