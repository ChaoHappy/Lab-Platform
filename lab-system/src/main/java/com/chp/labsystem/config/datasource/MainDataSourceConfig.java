package com.chp.labsystem.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PropertySourceFactory;

import javax.sql.DataSource;

/**
 * 类功能描述：主系统数据源配置
 */
@Configuration
@PropertySource(value = "classpath:dbconfiguration.yml", factory = PropertySourceFactory.class)
@ConfigurationProperties(prefix = "jdbc")
public class MainDataSourceConfig extends BaseDataSourceConfig {

    @Primary
    @Bean(name = "dataSource")
    public DataSource buildDataSource() {
        return super.buildDataSource();
    }
}
