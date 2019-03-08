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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * https://blog.csdn.net/tuesdayma/article/details/81081666
 * @author Yang.Qinghui
 *
 */
@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages="com.yqh.boot.dao.dsp",sqlSessionFactoryRef="dspDataSqlSessionFactory")
public class DspDataSourceConfig {
	
	// 将这个对象放入Spring容器中
	@Bean(name = "dspDataSource")
	// 表示这个数据源是默认数据源
	@Primary
	// 读取application.properties中的配置参数映射成为一个对象，prefix表示参数的前缀
	@ConfigurationProperties(prefix="spring.datasource.dsp")
	public DataSource dspDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dspDataSqlSessionFactory")
	@Primary
	// @Qualifier表示查找Spring容器中名字为dspDataSource的对象
	public SqlSessionFactory  dspSqlSessionFactory(@Qualifier("dspDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
				 // 设置mybatis的xml所在位置
				new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		return bean.getObject();
	}
 
	@Bean(name = "dspDataTransactionManager")
	@Primary
	public DataSourceTransactionManager dspTransactionManager(@Qualifier("dspDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
 
	@Bean(name = "dspDataSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate dspSqlSessionTemplate(
			@Qualifier("dspDataSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	
}

