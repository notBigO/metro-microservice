package com.metroservice.metro.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroservice.metro.dtos.StationDTO;
import com.metroservice.metro.services.StationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @GetMapping
    public ResponseEntity<List<StationDTO>> getAllActiveStations() {
        return ResponseEntity.ok(stationService.getAllActiveStations());
    }
}
