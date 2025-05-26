package com.faycal.outboxcore.entity;

import com.faycal.outboxcore.entity.enums.TruckMessageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * This class represents a truck message in the outbox pattern.
 * It contains the message details, status, and the time it was created.
 * The message can be sent later by the application.
 */
@Entity
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class TruckMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant dateTime;

    private String buCode;

    @Enumerated(EnumType.STRING)
    private TruckMessageStatus status;

    // This column contains the readable data, it's used by us to read the data in the database
    @Column(columnDefinition = "TEXT")
    private String messageRecordText;

    // This column contains the non-readable data, it's used by the application to store the messages
    @Embedded
    private MessageRecord messageRecord;

    @Column(name = "event_type")
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckMessage truckMessage = (TruckMessage) o;
        return Objects.equals(id, truckMessage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
