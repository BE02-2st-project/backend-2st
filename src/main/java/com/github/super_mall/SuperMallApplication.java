package com.github.super_mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SuperMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperMallApplication.class, args);
    }

}
