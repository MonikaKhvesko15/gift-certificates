package com.epam.esm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;

@ComponentScan(basePackages = "com.epam.esm")
@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class DBConfig{

    @Value("${driverClassName}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @Value("${maxPoolSize}")
    private int maxPoolSize;

//    @Bean
//    @Profile("dev")
//    public DataSource h2DataSource() {
//        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
//        return databaseBuilder.setType(EmbeddedDatabaseType.H2)
//                .addScripts("classpath:sql/ddl.sql", "classpath:sql/script.sql", "classpath:sql/test-data.sql")
//                .build();
//    }

    @Bean
    //@Profile("prod")
    public DataSource postgresDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        dataSource.setMaximumPoolSize(maxPoolSize);

        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(postgresDataSource());
    }
}
