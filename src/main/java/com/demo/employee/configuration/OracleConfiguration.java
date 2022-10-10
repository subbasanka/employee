package com.demo.employee.configuration;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.pool.OracleDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
//import javax.validation.constraints.NotNull;
import java.sql.SQLException;

@Data
@Component
@Configuration
@ConfigurationProperties("spring.datasource")
@EnableConfigurationProperties
@EnableJpaAuditing
@Slf4j
public class OracleConfiguration {
    private String username;
    private String password;
    private String url;

    @Bean
    @Primary
    public DataSource dataSource(Environment environment) throws SQLException {
        log.info("In datasource config method");
        String password = environment.getProperty(username);
        if(StringUtils.isBlank(password))
        {
            log.info("password is empty");
        }
        else {
            log.info("Retrieved DB credentials for user {}",username);
        }
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        return dataSource;
    }
}
