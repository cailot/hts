package au.org.htsv.hips.report;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import au.org.htsv.hips.report.entity.ExceptionSimpleData;
import au.org.htsv.hips.report.service.ExceptionReportService;
import au.org.htsv.hips.report.service.UserAccountService;
import au.org.htsv.hips.report.util.ExceptionReportConstants;

@EnableWebSecurity
public class HipsReportSecurity extends WebSecurityConfigurerAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfigurerAdapter.class);
	 
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ExceptionReportService exceptionReportService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Bean
	public HipsReportAuthenticationSuccessHandler hipsReportAuthenticationSuccessHandler() {
	    return new HipsReportAuthenticationSuccessHandler(userAccountService);
	}
	
	@Bean
	public HipsReportAuthenticationFailHandler hipsReportAuthenticationFailHandler() {
	    return new HipsReportAuthenticationFailHandler(userAccountService);
	}
	
	private String[] allRoles;
	
	//private String[] noViewerRoles;
	
	public static List<ExceptionSimpleData> agencies;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/assets/css/**","/assets/js/**","/assets/fonts/**","/assets/images/**"); // excluding folders list
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// set agency list including Administrator
		setAgencies();
		
		http.headers().frameOptions().sameOrigin();// allow iframe to embed PDF in body
		
		http
			.authorizeRequests()
				.antMatchers("/dashboard").hasAnyRole(allRoles)
				.antMatchers("/summary").hasAnyRole(allRoles)
				.antMatchers("/detail").hasAnyRole(allRoles)
				//.antMatchers("/detail").hasAnyRole(noViewerRoles)
				.antMatchers("/audit").hasAnyRole(allRoles)
				.antMatchers("/user").hasAnyRole(allRoles)
				.antMatchers("/singleHpii").hasAnyRole(allRoles)
				.antMatchers("/batchHpii").hasAnyRole(allRoles)
				.antMatchers("/docoUG").hasAnyRole(allRoles)
				.antMatchers("/docoEM").hasAnyRole(allRoles)
				.antMatchers("/docoHS").hasAnyRole(allRoles)
				.antMatchers("/", "static/**", "/login").permitAll()
		.and()
			.formLogin()
				.loginPage("/login") // login page link
				.loginProcessingUrl("/processLogin")
				.defaultSuccessUrl("/dashboard")// redirect link after login
			    .successHandler(hipsReportAuthenticationSuccessHandler()) // success login handler
			    .failureHandler(hipsReportAuthenticationFailHandler()) // fail login handler
				.permitAll()
		.and()
			.logout()
				.logoutSuccessUrl("/login")// redirect url after logout
				.invalidateHttpSession(true)// make session unavailable
				.permitAll();
		
	}
	

	/**
	 * load all agencies from DB and save them into static valuable 'agencies'
	 */
	private void setAgencies() {
		agencies = exceptionReportService.getAgencyList();
		allRoles = new String[agencies.size() + 2];
		allRoles[0] = ExceptionReportConstants.ADMINISTRATOR;
		allRoles[1] = ExceptionReportConstants.VIEWER;
		
		
		// because Viewer only uses statistical menus - dashboard, static
		//noViewerRoles = new String[agencies.size() + 1];
		//noViewerRoles[0] = ExceptionReportConstants.ADMINISTRATOR;
		
		int index = 2; // as 0 & 1 are already populated with Administrator & Viewer
		for(ExceptionSimpleData data : agencies) {
			String agency = data.getDisplay();
			agency = agency.replaceAll("'", "''"); // avoid escape character issue
			allRoles[index] = agency;
			//data.setValue(agency);
			index++;
		}
		
		LOG.debug("All agencies: " + Arrays.toString(agencies.toArray()));
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
