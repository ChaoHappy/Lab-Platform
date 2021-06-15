package com.chp.labsystem.config.sessionfactory;


import com.chp.labsystem.config.sessionfactory.nameingstrategy.ImplicitNamingStrategy;
import com.chp.labsystem.config.sessionfactory.nameingstrategy.PhysicalNamingStrategy;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

public class BaseSessionFactoryConfig {
    private String dialect;

    private String packagesToScan;

    private Boolean showSql;

    private String interceptor;

    public LocalSessionFactoryBean buildSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(getPackagesToScan());
        sessionFactory.setHibernateProperties(buildHibernateProperties());
        sessionFactory.setImplicitNamingStrategy(new ImplicitNamingStrategy());
        sessionFactory.setPhysicalNamingStrategy(new PhysicalNamingStrategy());
        return sessionFactory;
    }

    private Properties buildHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", getDialect());
        properties.put("hibernate.show_sql", getShowSql());
        String interceptor = getInterceptor();
        if (interceptor != null) {
            properties.put("hibernate.session_factory.interceptor", interceptor);
        }
        return properties;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getPackagesToScan() {
        return packagesToScan;
    }

    public void setPackagesToScan(String packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    public Boolean getShowSql() {
        if (showSql == null) {
            showSql = false;
        }
        return showSql;
    }

    public void setShowSql(Boolean showSql) {
        this.showSql = showSql;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }
}
