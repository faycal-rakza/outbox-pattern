package com.faycal.outboxcore.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MessageRecord can contain any Kafka message, with a header and a content (event).
 * We use this class to store the messages in the database via JPA and send them later with the outbox pattern.
 */
@Getter
@Setter
@Embeddable
public class MessageRecord implements Serializable {
    private String header;
    private SpecificRecordBase event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageRecord messageRecord = (MessageRecord) o;
        return Objects.equals(event, messageRecord.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }
}
