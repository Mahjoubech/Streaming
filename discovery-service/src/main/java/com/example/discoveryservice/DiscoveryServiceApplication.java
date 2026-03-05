package com.example.discoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Discovery Service — acts as the service registry for all microservices.
 *
 * STREAM-9:  Spring Cloud Netflix Eureka Server dependency added
 * STREAM-11: Self-registration disabled via application.yaml
 * STREAM-12: Runs on port 8761 (configured in application.yaml)
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceApplication.class, args);
    }
}
