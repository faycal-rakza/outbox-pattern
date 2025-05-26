package com.faycal.outboxsave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.faycal.outboxcore.repository")
@EntityScan(basePackages = "com.faycal.outboxcore.entity")
@SpringBootApplication(scanBasePackages = {"com.faycal.outboxsave", "com.faycal.outboxcore"})
public class OutboxSaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboxSaveApplication.class, args);
    }

}
