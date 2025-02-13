package com.metroservice.metro.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metroservice.metro.dtos.RouteDTO;
import com.metroservice.metro.entities.Route;
import com.metroservice.metro.entities.Station;
import com.metroservice.metro.exceptions.MetroException;
import com.metroservice.metro.repositories.RouteRepository;
import com.metroservice.metro.repositories.StationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;

    private RouteDTO convertToDTO(Route route) {
        RouteDTO dto = new RouteDTO();
        dto.setId(route.getId());
        dto.setSourceStationId(route.getSourceStation().getId());
        dto.setDestinationStationId(route.getDestinationStation().getId());
        dto.setDistance(route.getDistance());
        dto.setActive(route.isActive());
        return dto;
    }

    public List<RouteDTO> getAllActiveRoutes() {
        log.debug("Fetching all active routes");

        return routeRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public RouteDTO createRoute(RouteDTO routeDTO) {
        log.debug("Creating new route between stations: {} and {}",
                routeDTO.getSourceStationId(), routeDTO.getDestinationStationId());

        Station sourceStation = stationRepository.findById(routeDTO.getSourceStationId())
                .orElseThrow(() -> new MetroException("Source station not found", HttpStatus.NOT_FOUND));

        Station destinationStation = stationRepository.findById(routeDTO.getDestinationStationId())
                .orElseThrow(() -> new MetroException("Destination station not found", HttpStatus.NOT_FOUND));

        Optional<Route> existingRoute = routeRepository
                .findBySourceStationAndDestinationStationAndActiveTrue(sourceStation, destinationStation);

        if (existingRoute.isPresent()) {
            throw new MetroException("Route already exists between these stations", HttpStatus.BAD_REQUEST);
        }

        Route route = new Route();
        route.setSourceStation(sourceStation);
        route.setDestinationStation(destinationStation);
        route.setDistance(routeDTO.getDistance());
        route.setActive(true);

        return convertToDTO(routeRepository.save(route));
    }

    public Double calculateDistance(Station sourceStation, Station destinationStation) {
        Optional<Route> directRoute = routeRepository
                .findBySourceStationAndDestinationStationAndActiveTrue(sourceStation, destinationStation);

        if (directRoute.isPresent()) {
            return directRoute.get().getDistance();
        }

        throw new MetroException(
                "No route found between " + sourceStation.getName() + " and " + destinationStation.getName(),
                HttpStatus.NOT_FOUND);
    }
}
