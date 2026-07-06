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
public class StatsInfo implements Serializable {
	
	private String hospital;
	
	private String direction;
	
	private String time;
	
	private int count;
	
	private int standard;
	
	
}
