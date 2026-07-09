package au.org.htsv.hips.report.dao;

import java.util.List;
import java.util.function.Consumer;

import javax.persistence.Query;

public interface ArchiveQueryDAO {

	boolean isArchiveEnabled();

	int executeCount(String sql, Consumer<Query> parameterConfigurer, String from, String to);

	List<Object[]> executeList(String sql, Consumer<Query> parameterConfigurer, String from, String to);

	String executeSingleResult(String sql, Consumer<Query> parameterConfigurer);

	Object[] executeSingleRow(String sql, Consumer<Query> parameterConfigurer);
}
