package au.org.dashboard.auth.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Serializable{
	
	private String username;
	
	private String password;
	
	private int enabled;
	
	private String firstname;
	
	private String lastname;
	
	private String role;

	public UserData(Object[] columns) {
		this.username = StringUtils.defaultString((String) columns[0], "");
		this.password = StringUtils.defaultString((String) columns[1], "");
		this.enabled = (columns[2] != null) ? ((Integer) columns[2]) : 0;
		this.firstname = StringUtils.defaultString((String) columns[3], "");
		this.lastname = StringUtils.defaultString((String) columns[4], "");
		this.role = StringUtils.defaultString((String) columns[5], "");
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
	    return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	public Integer getEnabled() {
	    return enabled;
	}
	
	@Override
	public String toString() {
		return "Username: "+username+
				", Password: "+password+ 
				", Enabled: "+enabled+ 
				", FirstName: "+firstname+ 
				", LastName: "+lastname+ 
				", Role: "+role;
	}
	
}
