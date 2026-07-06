package au.org.hts.dashboard.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
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
public class Organisation implements Serializable {
	private String id;
	
	private String name;

	private String acronym;
	
	private String person;
	
	private String contact;
	
	private String email;
	
	private String address;

	@Getter(AccessLevel.NONE)
	private int inboundTotal = 0;

	@Getter(AccessLevel.NONE)
	private int outboundTotal = 0;
	
	private List<CommPoint> compoints = new ArrayList<CommPoint>();

	public int getInboundTotal() {
		if(compoints!=null && compoints.size()>0) {
			for(CommPoint compoint : compoints) {
				int inbound = compoint.getInQueueSize();
				inboundTotal += inbound;
			}
		}
		return inboundTotal;
	}
	
	public int getOutboundTotal() {
		if(compoints!=null && compoints.size()>0) {
			for(CommPoint compoint : compoints) {
				int outbound = compoint.getOutQueueSize();
				outboundTotal += outbound;
			}
		}
		return outboundTotal;
	}

}
