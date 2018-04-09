package com.someairlines.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.Driver;

@Configuration
@ComponentScan("com.someairlines.db")
@EnableTransactionManagement
public class DataConfig {
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
	    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource(dataSource());
	    sessionFactoryBean.setPackagesToScan("com.someairlines.entity");
	    sessionFactoryBean.setHibernateProperties(hibernateProperties());
	    return sessionFactoryBean;
	}

	private Properties hibernateProperties() {
	    Properties properties = new Properties();
	    properties.put("hibernate.dialect", org.hibernate.dialect.MySQL5Dialect.class.getName());
	    properties.put("hibernate.hbm2ddl.auto", "update");
	    properties.put("hibernate.id.new_generator_mappings", false);
	    properties.put("hibernate.c3p0.min_size", 5);
	    properties.put("hibernate.c3p0.max_size", 20);
	    properties.put("hibernate.c3p0.timeout", 300);
	    properties.put("hibernate.c3p0.max_statements", 20);
	    properties.put("hibernate.c3p0.idle_test_period", 3000);
	    properties.put("hibernate.cache.use_second_level_cache", "true");
	    properties.put("hibernate.cache.region.factory_class", EhCacheRegionFactory.class);
	    properties.put("net.sf.ehcache.configurationResourceName", "ehcache.xml");
	    properties.put("hibernate.cache.use_query_cache", true);
	    return properties;
	}

	@Bean
	public DataSource dataSource() {
	    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
	    dataSource.setDriverClass(Driver.class);
	    dataSource.setUrl("jdbc:mysql://localhost:3306/airport");
	    dataSource.setUsername("judge73");
	    dataSource.setPassword("wasd");
	    return dataSource;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
	   HibernateTransactionManager txManager = new HibernateTransactionManager();
	   txManager.setSessionFactory(sessionFactory);
	   return txManager;
	}
	
}
