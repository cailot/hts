package au.org.htsv.hips.report.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
public class ExceptionInitialData  extends ExceptionBasicData{

	private String documentType;
	private int successCnt;
	private int failCnt;
	private int totalCnt;
	
	public ExceptionInitialData(String fromDate, String toDate, String campus, String hospital) {
		super(fromDate, toDate, campus, hospital);
		// TODO Auto-generated constructor stub
	}

}
