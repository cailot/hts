package au.org.htsv.hips.report.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.org.htsv.hips.report.util.ArchiveDateRangeUtils;
import au.org.htsv.hips.report.util.ArchiveQueryPlan;
import au.org.htsv.hips.report.util.ExceptionReportConstants;

@Component
public class ArchiveAwareQueryExecutor {

	@Autowired
	private EntityManager entityManager;

	@Autowired(required = false)
	@Qualifier("archiveEntityManager")
	private EntityManager archiveEntityManager;

	@Value("${report.archive.cutoff-months:3}")
	private int archiveCutoffMonths;

	public boolean isArchiveEnabled() {
		return archiveEntityManager != null;
	}

	public int executeCount(String sql, Consumer<Query> parameterConfigurer, String from, String to) {
		if (!isArchiveEnabled()) {
			return executeSingleCount(entityManager, sql, parameterConfigurer, from, to);
		}

		ArchiveQueryPlan plan = ArchiveDateRangeUtils.buildPlan(from, to, archiveCutoffMonths);
		int total = 0;
		if (plan.isQueryPrimary()) {
			total += executeSingleCount(entityManager, sql, parameterConfigurer, plan.getPrimaryFrom(), plan.getPrimaryTo());
		}
		if (plan.isQueryArchive()) {
			total += executeSingleCount(archiveEntityManager, sql, parameterConfigurer, plan.getArchiveFrom(), plan.getArchiveTo());
		}
		return total;
	}

	public List<Object[]> executeList(String sql, Consumer<Query> parameterConfigurer, String from, String to) {
		if (!isArchiveEnabled()) {
			return executeSingleList(entityManager, sql, parameterConfigurer, from, to);
		}

		ArchiveQueryPlan plan = ArchiveDateRangeUtils.buildPlan(from, to, archiveCutoffMonths);
		List<Object[]> results = new ArrayList<>();
		if (plan.isQueryArchive()) {
			results.addAll(executeSingleList(archiveEntityManager, sql, parameterConfigurer, plan.getArchiveFrom(), plan.getArchiveTo()));
		}
		if (plan.isQueryPrimary()) {
			results.addAll(executeSingleList(entityManager, sql, parameterConfigurer, plan.getPrimaryFrom(), plan.getPrimaryTo()));
		}
		return results;
	}

	public String executeSingleResult(String sql, Consumer<Query> parameterConfigurer) {
		try {
			return executeSingleResult(entityManager, sql, parameterConfigurer);
		} catch (NoResultException primaryException) {
			if (!isArchiveEnabled()) {
				throw primaryException;
			}
			return executeSingleResult(archiveEntityManager, sql, parameterConfigurer);
		}
	}

	public Object[] executeSingleRow(String sql, Consumer<Query> parameterConfigurer) {
		try {
			return executeSingleRow(entityManager, sql, parameterConfigurer);
		} catch (NoResultException primaryException) {
			if (!isArchiveEnabled()) {
				throw primaryException;
			}
			return executeSingleRow(archiveEntityManager, sql, parameterConfigurer);
		}
	}

	private int executeSingleCount(EntityManager manager, String sql, Consumer<Query> parameterConfigurer,
			String from, String to) {
		Query query = manager.createNativeQuery(sql);
		parameterConfigurer.accept(query);
		query.setParameter(ExceptionReportConstants.FROM_DATE, from);
		query.setParameter(ExceptionReportConstants.TO_DATE, to);
		Integer count = (Integer) query.getSingleResult();
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	private List<Object[]> executeSingleList(EntityManager manager, String sql, Consumer<Query> parameterConfigurer,
			String from, String to) {
		Query query = manager.createNativeQuery(sql);
		parameterConfigurer.accept(query);
		query.setParameter(ExceptionReportConstants.FROM_DATE, from);
		query.setParameter(ExceptionReportConstants.TO_DATE, to);
		return query.getResultList();
	}

	private String executeSingleResult(EntityManager manager, String sql, Consumer<Query> parameterConfigurer) {
		Query query = manager.createNativeQuery(sql);
		parameterConfigurer.accept(query);
		return (String) query.getSingleResult();
	}

	private Object[] executeSingleRow(EntityManager manager, String sql, Consumer<Query> parameterConfigurer) {
		Query query = manager.createNativeQuery(sql);
		parameterConfigurer.accept(query);
		return (Object[]) query.setMaxResults(1).getSingleResult();
	}
}
