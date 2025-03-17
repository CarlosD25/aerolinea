package edu.unimagdalena.sistemavuelo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestContainerSettings {
    @Bean
    @ServiceConnection
   public PostgreSQLContainer<?> postgreSQLContainer(){
        return new PostgreSQLContainer<>("postgres:11-alpine")
                .withDatabaseName("sistemavuelo_db")
                .withUsername("postgres")
                .withPassword("1976");

    };
}
