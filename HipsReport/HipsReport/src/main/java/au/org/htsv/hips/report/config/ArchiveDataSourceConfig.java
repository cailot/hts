package au.org.htsv.hips.report.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@ConditionalOnProperty(name = "spring.archive.url")
@EnableConfigurationProperties(ArchiveDataSourceProperties.class)
public class ArchiveDataSourceConfig {

	@Bean(name = "archiveDataSource")
	public DataSource archiveDataSource(ArchiveDataSourceProperties archiveDataSourceProperties) {
		return archiveDataSourceProperties.buildDataSource();
	}

	@Bean(name = "archiveEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean archiveEntityManagerFactory(
			EntityManagerFactoryBuilder builder,
			@Qualifier("archiveDataSource") DataSource archiveDataSource) {
		return builder
				.dataSource(archiveDataSource)
				.packages("au.org.htsv.hips.report")
				.persistenceUnit("archive")
				.build();
	}
}
