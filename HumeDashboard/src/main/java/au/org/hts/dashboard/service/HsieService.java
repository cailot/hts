package au.org.hts.dashboard.service;

import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import au.org.hts.dashboard.entity.CommPoint;
import au.org.hts.dashboard.entity.Organisation;

public interface HsieService {
	
	Map<String, Object> getDashInfo(String engine) throws ParseException;
	
	List<Organisation> getOrganisations(String engine) throws ParseException;
	
	List<CommPoint> getCommPoints(String portfolio, String acronym, String project) throws ParseException;
	
	
	////////// P6 ///////////////////////
	List<CommPoint> getCommPoints();
	
	// including down commpoints
	List<CommPoint> getAllCommPoints();
	
	Map<String, Object> getDashInfo();

	
}
