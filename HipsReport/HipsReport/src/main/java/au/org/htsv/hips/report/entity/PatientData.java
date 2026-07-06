package au.org.htsv.hips.report.entity;

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
public class PatientData implements Serializable{
	
	private String firstName;
	
	private String lastName;
	
	private String gender;
	
	private String dob;
	
	private String address;
	
	private String urn;
	
	private String ihi;
	
	private String ihiStatus;
	
	private String medicare;
	
	private String dva;
	
	public PatientData(Object[] columns) {
		this.firstName = StringUtils.defaultString((String) columns[0], "");
		this.lastName = StringUtils.defaultString((String) columns[1], "");
		this.gender = (columns[2] != null) ? ((Integer) columns[2]).toString() : "0";
		this.dob = StringUtils.defaultString((String) columns[3], "");
		this.address = StringUtils.defaultString((String) columns[4], "");
		this.urn = StringUtils.defaultString((String) columns[5], "");
		this.ihi = StringUtils.defaultString((String) columns[6], "");
		this.ihiStatus = StringUtils.defaultString((String) columns[7], "");
		this.medicare = StringUtils.defaultString((String) columns[8], "");
		this.dva = StringUtils.defaultString((String) columns[9], "");
		
	}
	
}
