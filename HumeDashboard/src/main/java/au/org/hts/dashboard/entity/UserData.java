package au.org.hts.dashboard.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserData implements UserDetails{
	
	private String username;
	
	private String password;
	
	private int enabled;
	
    @JsonProperty("firstname")
	private String firstName;
	
    @JsonProperty("lastname")
	private String lastName;
	
	private String role;

	public UserData(Object[] columns) {
		this.username = StringUtils.defaultString((String) columns[0], "");
		this.password = StringUtils.defaultString((String) columns[1], "");
		this.enabled = (columns[2] != null) ? ((Integer) columns[2]) : 0;
		this.firstName = StringUtils.defaultString((String) columns[3], "");
		this.lastName = StringUtils.defaultString((String) columns[4], "");
		this.role = StringUtils.defaultString((String) columns[5], "");
	}
	
	public UserDetails toUserDetails() {
//		return new User(
//				this.username,
//				this.password,
//				this.enabled == 1,
//				true, // accountNonExpired 
//				true, // credentialsNonExpired
//				true, // accountNonLocked
//				Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role))
//		);
		return this;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled == 1;
	}
}
