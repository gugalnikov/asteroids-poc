package com.example.asteroidspoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AsteroidsPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsteroidsPocApplication.class, args);
    }
}
