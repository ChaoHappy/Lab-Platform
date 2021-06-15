package com.chp.labsystem.config.transaction;

import org.hibernate.SessionFactory;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 类功能描述：事务配置
 */
@Configuration
@EnableAspectJAutoProxy
@PropertySource(value = "classpath:dbconfiguration.yml")
@ConfigurationProperties(prefix = "spring")
public class MainTransactionConfig extends BaseTransactionConfig {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Bean(name = "txAdvice")
    public TransactionInterceptor txAdvice() {
        return buildTransactionInterceptor(new HibernateTransactionManager(sessionFactory));
    }

    @Bean(name = "advisor")
    public Advisor aopConfig(@Autowired @Qualifier("txAdvice") TransactionInterceptor transactionInterceptor) {
        return buldAopConfig(transactionInterceptor);
    }
}
