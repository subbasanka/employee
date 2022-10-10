package com.demo.employee.configuration;

import com.sun.istack.NotNull;
import lombok.Data;
import oracle.jdbc.pool.OracleDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
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
public class OracleConfiguration {
    private String username;
    private String password;
    private String url;

    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        return dataSource;
    }
}
