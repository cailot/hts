package au.org.htsv.hips.report.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import au.org.htsv.hips.report.entity.HpiiDTO;
import au.org.htsv.hips.report.util.ExceptionReportConstants;


@Repository
public class HpiiLookupDaoImpl implements HpiiLookupDao  {

	@Autowired
	private EntityManager entityManager;

	@Value("${sql.report.get.hpii}")
	private String getHpii;

	@Value("${sql.report.add.hpii}")
	private String addHpii;

	@Value("${sql.report.update.hpii}")
	private String updateHpii;

	@Override
	public HpiiDTO getHpii(String lastName, String firstName, String ahpra) {
		Query query = entityManager.createNativeQuery(getHpii);
		query.setParameter(ExceptionReportConstants.LAST_NAME, lastName);
		query.setParameter(ExceptionReportConstants.FIRST_NAME, firstName);
		query.setParameter(ExceptionReportConstants.AHPRA_NUMBER, ahpra);
		// make sure only one record returned
		HpiiDTO dto = new HpiiDTO((Object[]) query.setMaxResults(1).getSingleResult());
		return dto;
	}

	@Override
	public int addHpii(HpiiDTO dto) {
		Query query = entityManager.createNativeQuery(addHpii);
		query.setParameter(ExceptionReportConstants.LAST_NAME, dto.getLastName());
		query.setParameter(ExceptionReportConstants.FIRST_NAME, dto.getFirstName());
		query.setParameter(ExceptionReportConstants.AHPRA_NUMBER, dto.getAhpra());
		query.setParameter(ExceptionReportConstants.PROVIDER_NUMBER, dto.getProviderNumber());
		query.setParameter(ExceptionReportConstants.HPI_I, dto.getHpii());
		query.setParameter(ExceptionReportConstants.HPII_STATUS, dto.getStatus());
		return query.executeUpdate();
	}
	
	@Override
	public int updateHpii(HpiiDTO dto) {
		Query query = entityManager.createNativeQuery(updateHpii);
		query.setParameter(ExceptionReportConstants.LAST_NAME, dto.getLastName());
		query.setParameter(ExceptionReportConstants.FIRST_NAME, dto.getFirstName());
		query.setParameter(ExceptionReportConstants.AHPRA_NUMBER, dto.getAhpra());
		query.setParameter(ExceptionReportConstants.PROVIDER_NUMBER, dto.getProviderNumber());
		query.setParameter(ExceptionReportConstants.HPI_I, dto.getHpii());
		query.setParameter(ExceptionReportConstants.HPII_STATUS, dto.getStatus());
		return query.executeUpdate();
	}

}
