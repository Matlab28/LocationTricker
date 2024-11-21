package com.example.locationtricker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LocationTrickerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationTrickerApplication.class, args);
    }

}
