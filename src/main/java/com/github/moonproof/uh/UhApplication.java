package com.github.moonproof.uh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UhApplication {

    public static void main(String[] args) {
        SpringApplication.run(UhApplication.class, args);
    }

}