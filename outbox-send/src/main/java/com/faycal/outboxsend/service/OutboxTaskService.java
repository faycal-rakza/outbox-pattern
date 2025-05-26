package com.faycal.outboxsend.service;

import com.faycal.outboxcore.entity.TruckMessage;
import com.faycal.outboxcore.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxTaskService {

    private final MessageRepository messageRepository;
    private final OutboxSenderService outboxSenderService;

    /**
     * This method sends the messages that have to be sent.
     * The messages found in the database will be sent every x time.
     * The fixedDelayString is in ms.
     */
    @Scheduled(fixedDelayString = "5000")
    @Transactional(propagation = Propagation.NEVER)
    public void retryToSendAllMessages() {
        List<TruckMessage> truckMessages = messageRepository.findByStatusIsNullOrderByDateTimeAsc();
        truckMessages.forEach(outboxSenderService::sendMessage);
    }
}
