package com.faycal.outboxcore.entity.enums;

/* * This enum represents the status of a truck message in the outbox pattern.
 * It can either be SENT or ERROR, indicating whether the message was successfully sent or encountered an error during sending.
 */
public enum TruckMessageStatus {
    SENT,
    ERROR
}
