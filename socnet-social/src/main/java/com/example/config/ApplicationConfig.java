package com.example.config;

import com.example.domain.RequestScopeStorage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Database, {@link SessionFactory}, {@link ObjectMapper} configuration
 *
 * @author Andrew
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
public class ApplicationConfig {
    @Bean
    public DataSource dataSource(@Value("${spring.datasource.driverClassName}") String driverClassName,
                                 @Value("${spring.datasource.url}") String url,
                                 @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory(@Autowired DataSource dataSource,
                                         @Autowired Properties properties) throws IOException {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.example");
        factoryBean.setHibernateProperties(properties);
        factoryBean.setBootstrapExecutor(new SimpleAsyncTaskExecutor());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    @Bean
    public Properties properties(@Value("${spring.jpa.properties.hibernate.dialect}") String dialect,
                                 @Value("${spring.jpa.show-sql}") String showSQL,
                                 @Value("${spring.jpa.hibernate.ddl-auto}") String dllAuto) {
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, dialect);
        properties.put(AvailableSettings.SHOW_SQL, showSQL);
        properties.put(AvailableSettings.HBM2DDL_AUTO, dllAuto);
        return properties;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestScopeStorage storage() {
        return new RequestScopeStorage();
    }
}
