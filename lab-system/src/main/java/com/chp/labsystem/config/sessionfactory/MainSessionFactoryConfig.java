package com.chp.labsystem.config.sessionfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

/** 类功能描述：Hibernate SessionFactory配置 */
@Configuration
@PropertySource("classpath:dbconfiguration.yml")
@ConfigurationProperties(prefix = "hibernate")
public class MainSessionFactoryConfig extends BaseSessionFactoryConfig {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Primary
    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean buildSessionFactory() {
        return super.buildSessionFactory(dataSource);
    }
}
