package com.balai.user;

import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EntityScan("com.balai.user.model.entity") // ← scan for @Entity
@EnableJpaRepositories(basePackages = "com.balai.user.model.repository") // ← scan for your JPA repos
@EnableJpaAuditing
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    //    Logging Entity Detection
    @Bean
    public CommandLineRunner logEntities(EntityManager entityManager) {
        return args -> {
            entityManager.getMetamodel().getEntities().forEach(e ->
                    System.out.println("Entity detected: " + e.getName()));
        };
    }

}
