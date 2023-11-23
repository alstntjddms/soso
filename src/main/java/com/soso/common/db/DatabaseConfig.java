package com.soso.common.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    @Autowired
    GlobalPropertySource globalPropertySource;
    @Bean
    @Primary
    public DataSource customDataSource() {
        return DataSourceBuilder
                .create()
                .url(globalPropertySource.getUrl())
                .driverClassName(globalPropertySource.getDriverClassName())
                .username(globalPropertySource.getUsername())
                .password(globalPropertySource.getPassword())
                .build();
    }
}