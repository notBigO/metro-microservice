package com.metroservice.metro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metroservice.metro.entities.Station;
import com.metroservice.metro.entities.StationManager;

public interface StationManagerRepository extends JpaRepository<StationManager, Long> {
    List<StationManager> findByStationAndActiveTrue(Station station);
}