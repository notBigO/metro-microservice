package com.metroservice.metro.services;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.metroservice.metro.dtos.SOSAlert;
import com.metroservice.metro.entities.Station;
import com.metroservice.metro.entities.StationManager;
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

        List<StationManager> managers = stationManagerRepository.findByStationAndActiveTrue(station);

        if (managers.isEmpty()) {
            log.warn("No active station manager found for station: {}", station.getName());
            return;
        }

        managers.forEach(manager -> {
            SOSAlert sosAlert = new SOSAlert(userId, station.getName(), manager.getEmail());
            kafkaProducerService.sendSOSAlert(sosAlert);
            log.info("SOS alert published for station manager: {}", manager.getEmail());
        });
    }
}