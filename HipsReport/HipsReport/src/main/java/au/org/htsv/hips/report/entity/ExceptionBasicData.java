package au.org.htsv.hips.report.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class ExceptionBasicData  implements Serializable{

	// define fields
	private String fromDate;
	
	private String toDate;
	
	private String campus;
	
	private String hospital;
	
	private String facility;
	
	private String patientInfo;
	
	public ExceptionBasicData(String fromDate, String toDate, String campus) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.campus = campus;
	}

	public ExceptionBasicData(String fromDate, String toDate, String campus, String hospital) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.campus = campus;
		this.hospital = hospital;
	}

	public ExceptionBasicData(String fromDate, String toDate, String campus, String hospital, String facility) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.campus = campus;
		this.hospital = hospital;
		this.facility = facility;
	}
	
	
}
