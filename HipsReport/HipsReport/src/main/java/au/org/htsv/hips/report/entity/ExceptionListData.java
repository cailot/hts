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
public class ExceptionListData extends ExceptionBasicData {

	private String dateCreated;

	private String documentType;

	private String exception;
	
	private String userContextMessage;

	private String firstName;

	private String lastName;

	private String dob;

	private String gender;

	private String id;
	
	private String urNumber;
	
	private String admission;
	
	private String episode;
	
	public ExceptionListData(Object[] columns) {
		this.dateCreated = StringUtils.defaultString((String) columns[0], "");
		this.documentType = StringUtils.defaultString((String) columns[1], "");
		this.exception = StringUtils.defaultString((String) columns[2], "");
		this.firstName = StringUtils.defaultString((String) columns[3], "");
		this.lastName = StringUtils.defaultString((String) columns[4], "");
		this.dob = StringUtils.defaultString((String) columns[5], "");
		this.gender = StringUtils.defaultString((String) columns[6], "");
//		this.address =  StringUtils.defaultString((String) columns[7], "");
		this.urNumber = StringUtils.defaultString((String) columns[7], "");
		this.id = StringUtils.defaultString((String) columns[8], "");
		this.admission = StringUtils.defaultString((String) columns[9], "");
		//this.episode = StringUtils.defaultString((String) columns[10], "");
		this.episode = StringUtils.substringBefore((String) columns[10], "^");
		
	}
}
