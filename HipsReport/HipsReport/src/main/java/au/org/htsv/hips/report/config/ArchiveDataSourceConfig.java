package au.org.htsv.hips.report.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;

@Configuration
@ConditionalOnProperty(name = "spring.archive.datasource.url")
public class ArchiveDataSourceConfig {

	@Bean
	@ConfigurationProperties("spring.archive.datasource")
	public DataSourceProperties archiveDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "archiveDataSource")
	public DataSource archiveDataSource(
			DataSourceProperties archiveDataSourceProperties,
			DataSourceProperties springDataSourceProperties) {
		if (StringUtils.isBlank(archiveDataSourceProperties.getUsername())) {
			archiveDataSourceProperties.setUsername(springDataSourceProperties.getUsername());
			archiveDataSourceProperties.setPassword(springDataSourceProperties.getPassword());
		}
		return archiveDataSourceProperties.initializeDataSourceBuilder().build();
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

	@Bean(name = "archiveEntityManager")
	public EntityManager archiveEntityManager(
			@Qualifier("archiveEntityManagerFactory") LocalContainerEntityManagerFactoryBean archiveEntityManagerFactory) {
		EntityManagerFactory entityManagerFactory = archiveEntityManagerFactory.getObject();
		return SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory);
	}
}
