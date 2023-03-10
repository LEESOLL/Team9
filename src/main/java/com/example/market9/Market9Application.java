package com.example.market9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class Market9Application {

    public static void main(String[] args) {
        SpringApplication.run(Market9Application.class, args);
    }
    
}