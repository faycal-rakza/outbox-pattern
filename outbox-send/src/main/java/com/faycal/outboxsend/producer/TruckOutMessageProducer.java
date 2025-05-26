package com.faycal.outboxsend.producer;

import com.faycal.outboxsave.entity.TruckOutEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TruckOutMessageProducer extends KafkaProducer<TruckOutEvent> {
    public TruckOutMessageProducer(KafkaTemplate<String, TruckOutEvent> kafkaTemplate) {
        super(kafkaTemplate);
    }
}