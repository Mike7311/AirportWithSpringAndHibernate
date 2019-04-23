package com.someairlines.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories("com.someairlines.db")
@ComponentScan("com.someairlines.service")
public class DataConfig {
	
	@Autowired
	private Environment environment;
	
	private Properties hibernateProperties() {
	    Properties properties = new Properties();
	    properties.put("hibernate.dialect", org.hibernate.dialect.MySQL5Dialect.class.getName());
	    properties.put("hibernate.hbm2ddl.auto", "create");
	    properties.put("hibernate.id.new_generator_mappings", false);
	    properties.put("hibernate.cache.use_second_level_cache", "true");
	    properties.put("hibernate.cache.region.factory_class", EhCacheRegionFactory.class);
	    properties.put("net.sf.ehcache.configurationResourceName", "ehcache.xml");
	    properties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
	    properties.put("hibernate.cache.use_query_cache", true);
	    return properties;
	}
	
	@Bean
	public DataSource dataSource() {
	    HikariDataSource dataSource = new HikariDataSource();
	    dataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
	    dataSource.setJdbcUrl(environment.getProperty("spring.datasource.url"));
	    dataSource.setUsername(environment.getProperty("spring.datasource.username"));
	    dataSource.setPassword(environment.getProperty("spring.datasource.password"));
	    return dataSource;
	}
	
	@Bean
	  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setJpaProperties(hibernateProperties());
	    factory.setDataSource(dataSource());
	    factory.setPackagesToScan("com.someairlines.entity");
	    return factory;
	  }

	  @Bean
	  public PlatformTransactionManager transactionManager() {
	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory().getObject());
	    return txManager;
	  }
	
}
