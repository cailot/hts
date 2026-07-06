package au.org.dashboard.auth.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import au.org.dashboard.auth.entity.UserData;
import au.org.dashboard.auth.util.DashboardAuthenticateConstants;

@Repository
public class AuthUserAccountDAOImpl implements AuthUserAccountDAO {

	@Autowired
	private EntityManager entityManager;

	@Value("${sql.report.user.find}")
	private String userFind;

	@Value("${sql.report.login.success}")
	private String loginSuccess;

	@Value("${sql.report.login.fail}")
	private String loginFail;

	@Override
	public UserData findUserByUsername(String username) {
		Query query = entityManager.createNativeQuery(userFind);
		query.setParameter(DashboardAuthenticateConstants.USER_NAME, username);
		// make sure only one record returned
		UserData user = new UserData((Object[]) query.setMaxResults(1).getSingleResult());
		return user;
	}

	@Override
	@Transactional
	public int loginSuccess(String username) {
		//String username = user.getUsername();
		int result = entityManager.createNativeQuery(loginSuccess)
		.setParameter(DashboardAuthenticateConstants.USER_NAME, username)
		.executeUpdate();
		return result;
	}
	
	@Override
	@Transactional
	public int loginFail(String username) {
		//String username = user.getUsername();
		int result = entityManager.createNativeQuery(loginFail)
		.setParameter(DashboardAuthenticateConstants.USER_NAME, username)
		.executeUpdate();
		return result;
	}
}
