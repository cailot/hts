package au.org.hts.dashboard.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HL7Transaction implements Serializable {
	
	private String auditId;
	
	private String sendingApp;
	
	private String receivingApp;
	
	private String msgType;
	
	private String msgEvent;
	
	//private String direction;
	
	//private String msgCount;
	
	private String msgId;
	
	private String patientUr;
	
	private String patientFirstName;
	
	private String patientLastName;
	
	private String patientDob;
	
	private String patientGender;
	
	private String visitingId="";
	
	private String lastUpdate;
	
}
