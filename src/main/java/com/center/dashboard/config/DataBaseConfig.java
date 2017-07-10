package com.center.dashboard.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * Created by WYKIM on 2017-06-19.
 */
@Configuration
@EnableTransactionManagement
@Lazy
@MapperScan("com.center.dashboard.mapper")
@EnableConfigurationProperties({PropertyDataSource.class, PropertyMybatis.class})
public class DataBaseConfig implements TransactionManagementConfigurer {
    @Autowired
    private PropertyDataSource propertyDataSource;
    @Autowired
    private PropertyMybatis propertyMybatis;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(propertyDataSource.getDriverClassName());
        dataSource.setUrl(propertyDataSource.getUrl());
        dataSource.setUsername(propertyDataSource.getUserName());
        dataSource.setPassword(propertyDataSource.getPassword());
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        String mybatisConfigPath = propertyMybatis.getConfigPath();
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new ClassPathResource(mybatisConfigPath));
        return bean.getObject();
    }

    @Bean(destroyMethod = "clearCache")
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
