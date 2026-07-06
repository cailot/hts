package au.org.htsv.hips.report.service;

import java.util.List;

import au.org.htsv.hips.report.entity.HpiiDTO;


public interface HpiiLookupService {
	
	// get HPI-I from DB
	HpiiDTO getHpiiFromDB(HpiiDTO dto);
	
	// add HPI-I to DB
	int addHpiiToDB(HpiiDTO dto);
	
	// update HPI-I to DB
	int updateHpiiToDB(HpiiDTO dto);
		
	// get HPI-I : normal case
	String getHPII(String hosptial, String familyName, String givenName, String registerId) throws Exception;
	
	// get HPI-I : single name
	String getHPII(String hosptial, String familyName, String registerId) throws Exception;

	// individual get HPI-I : HpiiDTO
	HpiiDTO getHPII(HpiiDTO dto);
	
	// batch get HPI-I : HpiiDTO
	List<HpiiDTO> getHPII(List<HpiiDTO> dto);

}
