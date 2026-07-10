package au.org.htsv.hips.report.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Declaring any DataSource bean disables Spring Boot DataSource auto-config.
 * Primary must therefore be created explicitly from {@link DataSourceProperties}
 * (so {@code spring.datasource.url} maps correctly for Hikari as jdbcUrl).
 * Archive remains a separate non-primary DataSource.
 */
@Configuration
@EnableConfigurationProperties(ArchiveDataSourceProperties.class)
public class ArchiveDataSourceConfig {

	@Bean(name = "dataSource")
	@Primary
	public DataSource dataSource(DataSourceProperties dataSourceProperties) {
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean(name = "archiveDataSource")
	@ConditionalOnProperty(name = "spring.archive.url")
	public DataSource archiveDataSource(ArchiveDataSourceProperties archiveDataSourceProperties) {
		return archiveDataSourceProperties.buildDataSource();
	}
}
