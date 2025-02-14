package com.metroservice.metro.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metroservice.metro.dtos.StationDTO;
import com.metroservice.metro.entities.Station;
import com.metroservice.metro.repositories.StationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StationService {
    private final StationRepository stationRepository;

    private StationDTO convertToDTO(Station station) {
        StationDTO dto = new StationDTO();
        dto.setId(station.getId());
        dto.setName(station.getName());
        dto.setLocation(station.getLocation());
        dto.setActive(station.isActive());
        return dto;
    }

    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElseThrow();
    }

    public List<StationDTO> getAllActiveStations() {
        log.debug("Fetching all active stations");
        return stationRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StationDTO createStation(StationDTO stationDTO) {
        log.debug("Creating new station: {}", stationDTO);
        Station station = new Station();
        station.setName(stationDTO.getName());
        station.setLocation(stationDTO.getLocation());
        station.setActive(true);
        return convertToDTO(stationRepository.save(station));
    }
}
