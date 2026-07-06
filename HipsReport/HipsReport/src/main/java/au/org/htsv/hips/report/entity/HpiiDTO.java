package au.org.htsv.hips.report.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
//@NoArgsConstructor
@AllArgsConstructor

public class HpiiDTO implements Serializable {
	
	private String id;
		
	private String lastName;
	
	private String firstName;
			
	private String status;
	
	private String hpii;
	
	private LocalDateTime createDate;
	
	private String ahpra;
	
	private String providerNumber;
	
	private String hospitalCode;
	
	public HpiiDTO() {}
	
	public HpiiDTO(String lastName, String firstName, String ahpra) {
		 this.lastName = lastName;
		 this.firstName = firstName;
		 this.ahpra = ahpra;
	}
	
	public HpiiDTO(String hospitalCode, String lastName, String firstName, String ahpra) {
		 this.hospitalCode = hospitalCode;
		 this.lastName = lastName;
		 this.firstName = firstName;
		 this.ahpra = ahpra;
	}
	
	public HpiiDTO(String hospitalCode, String lastName, String firstName, String ahpra, String providerNumber) {
		 this.hospitalCode = hospitalCode;
		 this.lastName = lastName;
		 this.firstName = firstName;
		 this.ahpra = ahpra;
		 this.providerNumber = providerNumber;
		 
	}
	
	public HpiiDTO(int id, String lastName, String firstName, String ahpra, String hpii, String status) {
		 this.id = String.valueOf(id);
		 this.lastName = lastName;
		 this.firstName = firstName;
		 this.ahpra = ahpra;
		 this.hpii = hpii;
		 this.status = status;
	}
	
	public HpiiDTO(String lastName, String firstName, String ahpra, String providerNumber, String hpii, String status) {
		 this.lastName = lastName;
		 this.firstName = firstName;
		 this.ahpra = ahpra;
		 this.providerNumber = providerNumber;
		 this.hpii = hpii;
		 this.status = status;
	}
	
	public HpiiDTO(Object[] columns) {
//		this.id = StringUtils.defaultString(((Integer)columns[0]).toString(), "0");
		this.id = StringUtils.defaultString(String.valueOf(columns[0]), "0");
		this.lastName = StringUtils.defaultString((String) columns[1], "");
		this.firstName = StringUtils.defaultString((String) columns[2], "");
		this.ahpra = StringUtils.defaultString((String) columns[3], "");
		this.hpii = StringUtils.defaultString((String) columns[4], "");
		this.status = StringUtils.defaultString((String) columns[5], "");
		this.createDate = parseDate((Timestamp) columns[6]);
	}

	// convert Timestamp from DB to LocalDateTime in Java
	private LocalDateTime parseDate(Timestamp timestamp) {
	    try {
	        Instant instant = timestamp.toInstant();
	        ZoneId zoneId = ZoneId.systemDefault();
	        return instant.atZone(zoneId).toLocalDateTime();
	    } catch (Exception e) {
	        // Handle parsing exceptions
	        e.printStackTrace();
	        return null;
	    }
	}
	
}
