package com.ut.nlSystemAPi.config;

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

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.ut.nlSystemAPi.mapper.freedom", sqlSessionTemplateRef = "freedomSqlSessionTemplate")
public class DataSourceFreedomConfig {

    @Bean(name = "freedomDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.freedom")
    public DataSource freedomDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "freedomSqlSessionFactory")
    public SqlSessionFactory freedomSqlSessionFactory(@Qualifier("freedomDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // Load all Freedom mappers (current files live under mapper/freedom/mysql/)
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/freedom/**/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    @Bean(name = "freedomTransactionManager")
    public DataSourceTransactionManager freedomTransactionManager(@Qualifier("freedomDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "freedomSqlSessionTemplate")
    public SqlSessionTemplate freedomSqlSessionTemplate(@Qualifier("freedomSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
