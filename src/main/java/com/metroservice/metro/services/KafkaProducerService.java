package com.metroservice.metro.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metroservice.metro.dtos.FareProcessingEvent;
import com.metroservice.metro.dtos.SOSAlert;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendSOSAlert(SOSAlert alert) {
        String message = convertEventToJson(alert);
        log.info("Publishing SOS alert: {}", message);
        kafkaTemplate.send("sos-alerts", message);
    }

    public void sendFareProcessingEvent(FareProcessingEvent event) {
        String message = convertEventToJson(event);

        log.info("Publishing fare event: {}", message);
        kafkaTemplate.send("fare-processing", message);
    }

    private <T> String convertEventToJson(T event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event", e);
        }
    }
}
