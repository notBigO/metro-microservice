package com.metroservice.metro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.metroservice.metro.dtos.StationManagerDTO;
import com.metroservice.metro.entities.Station;
import com.metroservice.metro.entities.StationManager;
import com.metroservice.metro.exceptions.MetroException;
import com.metroservice.metro.repositories.StationManagerRepository;
import com.metroservice.metro.repositories.StationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StationManagerService {
    private final StationManagerRepository stationManagerRepository;
    private final StationRepository stationRepository;

    private StationManagerDTO mapToDTO(StationManager manager) {
        return new StationManagerDTO(
                manager.getId(),
                manager.getName(),
                manager.getEmail(),
                manager.getStation().getId(),
                manager.isActive());
    }

    public StationManagerDTO addStationManager(StationManagerDTO stationManagerDTO) {
        Station station = stationRepository.findById(stationManagerDTO.getStationId())
                .orElseThrow(() -> new MetroException("Station not found", HttpStatus.NOT_FOUND));

        StationManager manager = new StationManager();
        manager.setName(stationManagerDTO.getName());
        manager.setEmail(stationManagerDTO.getEmail());
        manager.setActive(stationManagerDTO.isActive());
        manager.setStation(station);

        StationManager savedManager = stationManagerRepository.save(manager);
        log.info("Added new station manager: {}", savedManager.getName());

        return mapToDTO(savedManager);
    }

    public List<StationManagerDTO> getManagersByStation(Long stationId) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new MetroException("Station not found", HttpStatus.NOT_FOUND));

        List<StationManager> managers = stationManagerRepository.findByStationAndActiveTrue(station);
        return managers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
