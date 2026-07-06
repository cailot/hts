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
public class Engine implements Serializable {
	
	private String version;

	private String name;

	private String uptime;

	private String availableDisk;

	private String totalDisk;

	private String availableMemory;

	private String totalMemory;
}
