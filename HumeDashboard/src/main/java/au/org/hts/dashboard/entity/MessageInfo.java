package au.org.hts.dashboard.entity;

import java.io.Serializable;

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
public class MessageInfo implements Serializable {
	
	private String type;
	
	private String event;
	
	private String description;
	
	private String inbound;
	
	private String outbound;
	
	public MessageInfo(String type, String event) {
		this.type = type;
		this.event = event;
	}
}
