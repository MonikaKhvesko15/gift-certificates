package com.epam.esm.config;

import com.epam.esm.repository.CertificateRepositoryImpl;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.TagRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class TestConfig {
    @Bean
    public DataSource h2DataSource() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder.setType(EmbeddedDatabaseType.H2)
                .setName("test")
                .ignoreFailedDrops(true)
                .addScript("classpath:sql/script.sql")
                .addScript("classpath:sql/db_functions.sql")
                .addScript("classpath:sql/test-data.sql")
                .build();
    }

    @Bean
    public TagRepository tagRepository() {
        return new TagRepositoryImpl(h2DataSource());
    }

    @Bean
    public CertificateRepositoryImpl certificateRepositoryImpl() {
        return new CertificateRepositoryImpl(h2DataSource());
    }
}
