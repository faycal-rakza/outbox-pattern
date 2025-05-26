package com.faycal.outboxsend.configuration.sender;

import com.faycal.outboxcore.entity.MessageRecord;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A simplified MessageSender that only handles truck events.
 */
@Component
@RequiredArgsConstructor
public class MessageSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends a list of messages.
     *
     * @param messages The list of message records containing the message header and event.
     * @param buCode   The business unit code.
     */
    public void send(List<MessageRecord> messages, String buCode) {
        messages.forEach(messageRecord -> this.send(buCode, messageRecord));
    }

    /**
     * Sends a single message.
     *
     * @param buCode        The business unit code.
     * @param messageRecord The message record containing the message header and event.
     */
    void send(String buCode, MessageRecord messageRecord) {
        String header = messageRecord.getHeader();
        SpecificRecordBase message = messageRecord.getEvent();
        String topicName = "truck-events-" + buCode.toLowerCase();

        // Create a producer record
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topicName, header, message);
        producerRecord.headers().add("ce_type", message.getClass().getSimpleName().getBytes());

        try {
            // Send the message and wait for the result
            var result = kafkaTemplate.send(producerRecord).get();
            LOGGER.info("Sent message=[ {} ] with topic=[ {} ] partition=[ {} ]  offset=[ {} ]",
                    message, result.getRecordMetadata().topic(), result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
        } catch (Exception e) {
            LOGGER.error("Unable to send message=[ {} ] due to : {}", message, e.getMessage());
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }
}
