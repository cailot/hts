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
public class ExceptionSimpleData  implements Serializable{

	// define fields
	private String display;
	
	private String value;

	public ExceptionSimpleData(Object[] columns) {
		this.display = StringUtils.defaultString((String) columns[0], "");
		this.value = (columns[1] != null) ? ((Integer) columns[1]).toString() : "0";
	}

}
