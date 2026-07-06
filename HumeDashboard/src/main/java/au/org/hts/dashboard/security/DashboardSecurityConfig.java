package au.org.hts.dashboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import au.org.hts.dashboard.service.LoginAuthenticationProvider;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class DashboardSecurityConfig {
	
	@Autowired
	private CustomLoginFailureHandler customLoginFailureHandler;


	private final LoginAuthenticationProvider eaiAuthenticationProvider;
	
	public DashboardSecurityConfig(LoginAuthenticationProvider eaiAuthenticationProvider) {
		this.eaiAuthenticationProvider = eaiAuthenticationProvider;
	}
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


		http.getSharedObject(AuthenticationManagerBuilder.class).parentAuthenticationManager(null); // avoid 2 times call

	    http
	    	.authenticationProvider(eaiAuthenticationProvider)
	        .authorizeRequests(authorize -> authorize
	        	.antMatchers("/css/**", "/js/**", "/img/**", "/vendor/**").permitAll()
		        .antMatchers("/login", "/processLogin").permitAll() // allow URL
	            .anyRequest().authenticated() // need authentication
	        )
	        .formLogin(form -> form
	            .loginPage("/login")
	            .loginProcessingUrl("/processLogin")
	            .failureHandler(customLoginFailureHandler) // append error message at URL
	            .defaultSuccessUrl("/monitor", true)// redirect link after login
	        )
	        .logout(logout -> logout.permitAll()
	        );

	    return http.build();
	}
}