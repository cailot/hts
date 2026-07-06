package au.org.htsv.hips.report.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
	
	private int loginfail;
	
	private Timestamp logintime;

	public UserData(Object[] columns) {
		this.username = StringUtils.defaultString((String) columns[0], "");
		this.password = StringUtils.defaultString((String) columns[1], "");
		this.enabled = (columns[2] != null) ? ((Integer) columns[2]) : 0;
		this.firstname = StringUtils.defaultString((String) columns[3], "");
		this.lastname = StringUtils.defaultString((String) columns[4], "");
		this.role = StringUtils.defaultString((String) columns[5], "");
		this.loginfail = (columns[6] != null) ? ((Integer) columns[6]) : 0;
		this.logintime = (columns[7] != null) ? ((Timestamp)columns[7]) : null;		
	}
	
}
