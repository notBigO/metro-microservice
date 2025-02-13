package com.metroservice.metro.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metroservice.metro.dtos.FareProcessingEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendFareProcessingEvent(FareProcessingEvent event) {
        String message = convertEventToJson(event);

        log.info("Publishing fare event: {}", message);
        kafkaTemplate.send("fare-processing", message);
    }

    private String convertEventToJson(FareProcessingEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing event", e);
        }
    }
}
