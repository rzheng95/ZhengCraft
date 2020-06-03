package com.rzheng.zhengcraft.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

public class PostgresDatasource {

    public static HikariDataSource hikariDataSource()  {
        
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "postgres");
        props.setProperty("dataSource.password", "admin");
        props.setProperty("dataSource.databaseName", "zhengcraft");

        HikariConfig config = new HikariConfig(props);
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

}
