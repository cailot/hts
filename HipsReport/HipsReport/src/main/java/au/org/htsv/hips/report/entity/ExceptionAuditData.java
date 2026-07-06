package au.org.htsv.hips.report.entity;

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
public class ExceptionAuditData extends ExceptionBasicData {

	private String dateCreated;

	private String firstName;

	private String lastName;
	
	private String urNumber;

	private String ihi;

	private String serviceName;
	
	private String documentName;

	private String accessBy;
	
	private String clinicianId;
	
	private String patientMasterId;

	public ExceptionAuditData(Object[] columns) {
		this.dateCreated = StringUtils.defaultString((String) columns[0], "");
		this.firstName = StringUtils.defaultString((String) columns[1], "");
		this.lastName = StringUtils.defaultString((String) columns[2], "");
		this.urNumber = StringUtils.defaultString((String) columns[3], "");
		this.ihi = StringUtils.defaultString((String) columns[4], "");
		this.serviceName = StringUtils.defaultString((String) columns[5], "");
		this.documentName = StringUtils.defaultString((String) columns[6], "");
		this.accessBy = StringUtils.defaultString((String) columns[7], "");
		this.clinicianId = StringUtils.defaultString((String) columns[8], "");
		this.patientMasterId =  (columns[7] != null) ? ((Integer) columns[9]).toString() : "0";
	}
}
