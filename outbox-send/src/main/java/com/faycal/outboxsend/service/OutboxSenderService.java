package com.faycal.outboxsend.service;

import com.faycal.outboxcore.entity.TruckMessage;
import com.faycal.outboxcore.entity.enums.TruckMessageStatus;
import com.faycal.outboxcore.repository.MessageRepository;
import com.faycal.outboxsend.configuration.sender.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxSenderService {
    private final MessageSender messageSender;
    private final MessageRepository messageRepository;

    /**
     * Sends the message contained in the TruckMessage entity via Kafka.
     * Uses the MessageSender to publish the message to the topic corresponding to the buCode.
     * Updates the message status to SENT if the "send" is successful, otherwise to ERROR.
     * Then saves the TruckMessage entity with the new status.
     *
     * @param truckMessage The TruckMessage entity to send and update.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendMessage(TruckMessage truckMessage) {
        try {
            messageSender.send(List.of(truckMessage.getMessageRecord()), truckMessage.getBuCode());
            truckMessage.setStatus(TruckMessageStatus.SENT);
        } catch (Exception e) {
            log.error("Can not send truckMessage ID {}, cause {}.", truckMessage.getId(), e.getMessage());
            truckMessage.setStatus(TruckMessageStatus.ERROR);
        }
        messageRepository.save(truckMessage);
    }
}