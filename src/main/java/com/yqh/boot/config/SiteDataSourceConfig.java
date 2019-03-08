package com.yqh.boot.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages="com.yqh.boot.dao.site",sqlSessionFactoryRef="siteDataSqlSessionFactory")
public class SiteDataSourceConfig {
	
	@Bean(name = "siteDataSource")	
	@ConfigurationProperties(prefix="spring.datasource.site") //告诉自动加载配置的属性
	public DataSource siteDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "siteDataSqlSessionFactory")
	public SqlSessionFactory  siteSqlSessionFactory(@Qualifier("siteDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		return bean.getObject();
	}
 
	@Bean(name = "siteDataTransactionManager")
	public DataSourceTransactionManager siteTransactionManager(@Qualifier("siteDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
 
	@Bean(name = "siteDataSqlSessionTemplate")
	public SqlSessionTemplate siteSqlSessionTemplate(
			@Qualifier("siteDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	
}

