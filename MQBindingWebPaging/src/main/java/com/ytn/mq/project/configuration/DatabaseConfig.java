package com.ytn.mq.project.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(value = "com.ytn.mq.project.dao")
public class DatabaseConfig {
	
	@Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

       SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
       sessionFactory.setDataSource(dataSource);

       PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
       sessionFactory.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));

       // 마이바티스 프로퍼티 설정
       Properties mybatisProperties = new Properties();
       mybatisProperties.setProperty("mapUnderscoreToCamelCase", "true"); // CamelCase 자동맵핑
       sessionFactory.setConfigurationProperties(mybatisProperties);

       return sessionFactory.getObject();
    }

}