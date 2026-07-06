package au.org.htsv.hips.report.dao;

import au.org.htsv.hips.report.entity.HpiiDTO;

public interface HpiiLookupDao {

	// get Hpii Object by condition
	HpiiDTO getHpii(String lastName, String firstName, String ahpra);
	
	// insert Hpii
	int addHpii(HpiiDTO dto);
	
	// update Hpii
	int updateHpii(HpiiDTO dto);
		
}
