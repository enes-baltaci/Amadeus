package com.example.amadeus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmadeusApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmadeusApplication.class, args);
    }

}
