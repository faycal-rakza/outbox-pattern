package com.faycal.outboxsave.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfiguration {

    /**
     * Return the system clock
     *
     * @return {@link Clock}
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
