package au.org.htsv.hips.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.stereotype.Repository;

import au.org.htsv.hips.report.util.ArchiveQueryPlan;
import au.org.htsv.hips.report.util.ExceptionReportConstants;
import au.org.htsv.hips.report.util.ExceptionReportUtils;

@Repository
public class ArchiveQueryDAOImpl implements ArchiveQueryDAO {

	private static final Logger LOG = LoggerFactory.getLogger(ArchiveQueryDAOImpl.class);

	@Autowired
	private EntityManager entityManager;

	@Autowired(required = false)
	@Qualifier("archiveEntityManagerFactory")
	private LocalContainerEntityManagerFactoryBean archiveEntityManagerFactoryBean;

	private EntityManager archiveEntityManager;

	@Value("${report.archive.cutoff-months:3}")
	private int archiveCutoffMonths;

	@PostConstruct
	public void initArchiveEntityManager() {
		if (archiveEntityManagerFactoryBean != null) {
			archiveEntityManager = SharedEntityManagerCreator.createSharedEntityManager(
					archiveEntityManagerFactoryBean.getObject());
		}
	}

	@Override
	public boolean isArchiveEnabled() {
		return archiveEntityManager != null;
	}

	@Override
	public int executeCount(String sql, Consumer<Query> parameterConfigurer, String from, String to) {
		if (!isArchiveEnabled()) {
			LOG.info("ArchiveQueryDAO executeCount fromDate={}, toDate={}, routing=archive disabled, primary datasource only", from, to);
			return executeSingleCount("primary", entityManager, sql, parameterConfigurer, from, to);
		}

		ArchiveQueryPlan plan = ExceptionReportUtils.buildArchiveQueryPlan(from, to, archiveCutoffMonths);
		LOG.info("ArchiveQueryDAO executeCount requested fromDate={}, toDate={}, routing={}",
				from, to, ExceptionReportUtils.describeArchiveRouting(from, to, archiveCutoffMonths));
		int total = 0;
		if (plan.isQueryPrimary()) {
			total += executeSingleCount("primary", entityManager, sql, parameterConfigurer, plan.getPrimaryFrom(), plan.getPrimaryTo());
		}
		if (plan.isQueryArchive()) {
			total += executeSingleCount("archive", archiveEntityManager, sql, parameterConfigurer, plan.getArchiveFrom(), plan.getArchiveTo());
		}
		return total;
	}

	@Override
	public List<Object[]> executeList(String sql, Consumer<Query> parameterConfigurer, String from, String to) {
		if (!isArchiveEnabled()) {
			LOG.info("ArchiveQueryDAO executeList fromDate={}, toDate={}, routing=archive disabled, primary datasource only", from, to);
			return executeSingleList("primary", entityManager, sql, parameterConfigurer, from, to);
		}

		ArchiveQueryPlan plan = ExceptionReportUtils.buildArchiveQueryPlan(from, to, archiveCutoffMonths);
		LOG.info("ArchiveQueryDAO executeList requested fromDate={}, toDate={}, routing={}",
				from, to, ExceptionReportUtils.describeArchiveRouting(from, to, archiveCutoffMonths));
		List<Object[]> results = new ArrayList<>();
		if (plan.isQueryArchive()) {
			results.addAll(executeSingleList("archive", archiveEntityManager, sql, parameterConfigurer, plan.getArchiveFrom(), plan.getArchiveTo()));
		}
		if (plan.isQueryPrimary()) {
			results.addAll(executeSingleList("primary", entityManager, sql, parameterConfigurer, plan.getPrimaryFrom(), plan.getPrimaryTo()));
		}
		return results;
	}

	@Override
	public String executeSingleResult(String sql, Consumer<Query> parameterConfigurer) {
		try {
			return executeSingleResult("primary", entityManager, sql, parameterConfigurer);
		} catch (NoResultException primaryException) {
			if (!isArchiveEnabled()) {
				throw primaryException;
			}
			LOG.info("ArchiveQueryDAO executeSingleResult fallback to archive datasource");
			return executeSingleResult("archive", archiveEntityManager, sql, parameterConfigurer);
		}
	}

	@Override
	public Object[] executeSingleRow(String sql, Consumer<Query> parameterConfigurer) {
		try {
			return executeSingleRow("primary", entityManager, sql, parameterConfigurer);
		} catch (NoResultException primaryException) {
			if (!isArchiveEnabled()) {
				throw primaryException;
			}
			LOG.info("ArchiveQueryDAO executeSingleRow fallback to archive datasource");
			return executeSingleRow("archive", archiveEntityManager, sql, parameterConfigurer);
		}
	}

	private int executeSingleCount(String dataSource, EntityManager manager, String sql, Consumer<Query> parameterConfigurer,
			String from, String to) {
		LOG.info("ArchiveQueryDAO [{}] executeCount fromDate={}, toDate={}", dataSource, from, to);
		Query query = manager.createNativeQuery(sql);
		parameterConfigurer.accept(query);
		query.setParameter(ExceptionReportConstants.FROM_DATE, from);
		query.setParameter(ExceptionReportConstants.TO_DATE, to);
		Integer count = (Integer) query.getSingleResult();
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	private List<Object[]> executeSingleList(String dataSource, EntityManager manager, String sql, Consumer<Query> parameterConfigurer,
			String from, String to) {
		LOG.info("ArchiveQueryDAO [{}] executeList fromDate={}, toDate={}", dataSource, from, to);
		Query query = manager.createNativeQuery(sql);
		parameterConfigurer.accept(query);
		query.setParameter(ExceptionReportConstants.FROM_DATE, from);
		query.setParameter(ExceptionReportConstants.TO_DATE, to);
		return query.getResultList();
	}

	private String executeSingleResult(String dataSource, EntityManager manager, String sql, Consumer<Query> parameterConfigurer) {
		LOG.info("ArchiveQueryDAO [{}] executeSingleResult", dataSource);
		Query query = manager.createNativeQuery(sql);
		parameterConfigurer.accept(query);
		return (String) query.getSingleResult();
	}

	private Object[] executeSingleRow(String dataSource, EntityManager manager, String sql, Consumer<Query> parameterConfigurer) {
		LOG.info("ArchiveQueryDAO [{}] executeSingleRow", dataSource);
		Query query = manager.createNativeQuery(sql);
		parameterConfigurer.accept(query);
		return (Object[]) query.setMaxResults(1).getSingleResult();
	}
}
