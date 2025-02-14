package com.metroservice.metro.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.metroservice.metro.dtos.SOSAlert;
import com.metroservice.metro.entities.Station;
import com.metroservice.metro.repositories.StationManagerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SOSService {
    private final StationManagerRepository stationManagerRepository;
    private final KafkaProducerService kafkaProducerService;

    public void handleSOSAlert(Station station, Long userId) {
        log.info("Processing SOS alert for user: {} at station: {}", userId, station.getName());

        stationManagerRepository.findByStationAndActiveTrue(station)
                .stream()
                .findFirst()
                .ifPresentOrElse(manager -> {
                    SOSAlert sosAlert = new SOSAlert(userId, station.getName(), manager.getEmail());
                    kafkaProducerService.sendSOSAlert(sosAlert);
                    log.info("SOS alert published for station manager: {}", manager.getEmail());
                }, () -> log.warn("No active station manager found for station: {}", station.getName()));
    }
}