package com.chp.labsystem.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

public class BaseDataSourceConfig {
    private String url;

    private String username;

    private String password;

    public DataSource buildDataSource() {
        if (url == null) {
            return null;
        }
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
