package com.simon.wigellpadel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WigellPadelApplication {

    public static void main(String[] args) {
        SpringApplication.run(WigellPadelApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(@Value("${server.port:8080}") String port) {
        return args -> {
            System.out.println("API started on port: " + port);
        };
    }
}
