package com.metroservice.metro.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metroservice.metro.entities.Route;
import com.metroservice.metro.entities.Station;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findBySourceStationAndDestinationStationAndActiveTrue(Station source, Station destination);

    List<Route> findByActiveTrue();
}