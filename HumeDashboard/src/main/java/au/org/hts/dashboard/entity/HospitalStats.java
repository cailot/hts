package au.org.hts.dashboard.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class HospitalStats implements Serializable {
	
	private String code;
	
	private String name;
	
	private List<StatsInfo> receivedStats = new ArrayList<StatsInfo>();

	private List<StatsInfo> sentStats = new ArrayList<StatsInfo>();
}
