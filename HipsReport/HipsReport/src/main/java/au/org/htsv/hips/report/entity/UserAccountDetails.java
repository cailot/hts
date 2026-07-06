package au.org.htsv.hips.report.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAccountDetails implements UserDetails {
	
	private String username;
	private String password;
	private int enabled;
	private String firstname;
	private String lastname;
	private List<GrantedAuthority> authorities;
	
	
	
	public UserAccountDetails(UserData user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.enabled = user.getEnabled();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
//		for(String role : user.getRoles()) {
//			GrantedAuthority auth = new SimpleGrantedAuthority(role);
//			auths.add(auth);
//		}
		auths.add(new SimpleGrantedAuthority(user.getRole()));
		this.authorities = auths;
	}
	
	

	public UserAccountDetails() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
