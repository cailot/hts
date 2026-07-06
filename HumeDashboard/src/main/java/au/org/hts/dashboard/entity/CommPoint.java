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
public class CommPoint implements Serializable {
	private String id;
	
	private String name;
	
	private String folderPath;
	
	private String mode;
	
	private String state;

	private int inQueueSize = 0;

	private int outQueueSize = 0;
	
	private String inputIdleTime;
	
	private String outputIdleTime;
	
	private String uptime;
	
	private int connectionCount = 0;
	
	private int receivedCount = 0;
	
	private int sentCount = 0;

}
