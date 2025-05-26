package com.faycal.outboxsend.producer;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public abstract class KafkaProducer<T extends SpecificRecord> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, T> kafkaTemplate;

    KafkaProducer(KafkaTemplate<String, T> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, String key, T message) {
        ProducerRecord<String, T> producerRecord = new ProducerRecord<>(topicName, key, message);
        producerRecord.headers().add("ce_type", message.getClass().getSimpleName().getBytes());

        CompletableFuture<SendResult<String, T>> future = kafkaTemplate.send(producerRecord);

        future.whenComplete((result, throwable) -> {
            if (throwable == null) {
                LOGGER.info("Sent message=[ {} ] with topic=[ {} ] partition=[ {} ]  offset=[ {} ]",
                        message, result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
            } else {
                LOGGER.error("Unable to send message=[ {} ] due to : {}", message, throwable.getMessage());
            }
        });
    }
}