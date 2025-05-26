package com.faycal.outboxsave.controller;

import com.faycal.outboxcore.entity.MessageRecord;
import com.faycal.outboxsave.entity.TruckOutEvent;
import com.faycal.outboxsave.service.TruckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trucks")
@RequiredArgsConstructor
@Slf4j
public class TruckController {
    private final TruckService truckService;

    @PostMapping("/message")
    public ResponseEntity<String> saveMessage(
            @RequestParam String buCode, 
            @RequestParam String header,
            @RequestParam String licensePlate,
            @RequestParam String color,
            @RequestParam String eventType) {
        try {
            // Create a TruckEvent instance
            SpecificRecordBase event = new TruckOutEvent(licensePlate, color, eventType);

            MessageRecord messageRecord = new MessageRecord();
            messageRecord.setHeader(header);
            messageRecord.setEvent(event);

            truckService.saveMessageToQueue(messageRecord, buCode);
            return ResponseEntity.ok("Message saved successfully");
        } catch (Exception e) {
            log.error("Error saving message", e);
            return ResponseEntity.badRequest().body("Error saving message: " + e.getMessage());
        }
    }

}
