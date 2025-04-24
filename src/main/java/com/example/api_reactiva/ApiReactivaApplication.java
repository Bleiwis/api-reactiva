package com.example.api_reactiva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Annotation that marks this class as the main entry point for a Spring Boot application
@SpringBootApplication
public class ApiReactivaApplication {

    // Main method: the entry point of the Java application
    public static void main(String[] args) {
        // SpringApplication.run: Bootstraps the application, starting the Spring context
        SpringApplication.run(ApiReactivaApplication.class, args);
    }

}
