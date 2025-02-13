package com.metroservice.metro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.metroservice.metro.entities.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
    List<Station> findByActiveTrue();

    @Query("SELECT s FROM Station s WHERE s.active = true AND s.name LIKE %:searchTerm%")
    List<Station> searchActiveStations(String searchTerm);
}