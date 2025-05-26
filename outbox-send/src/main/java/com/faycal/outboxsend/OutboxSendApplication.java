package com.faycal.outboxsend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.faycal.outboxsend", "com.faycal.outboxcore"})
@EntityScan(basePackages = "com.faycal.outboxcore.entity")
@EnableJpaRepositories(basePackages = "com.faycal.outboxcore.repository")
@EnableScheduling
public class OutboxSendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutboxSendApplication.class, args);
    }

}
