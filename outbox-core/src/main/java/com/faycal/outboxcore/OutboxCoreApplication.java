package com.faycal.outboxcore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.faycal.outboxcore.entity")
@SpringBootApplication
public class OutboxCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboxCoreApplication.class, args);
    }

}
