package com.metroservice.metro.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroservice.metro.services.SOSService;
import com.metroservice.metro.services.StationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sos")
@RequiredArgsConstructor

public class SOSController {
    private final SOSService sosService;
    private final StationService stationService;

    @PostMapping("/{stationId}/{userId}")
    public ResponseEntity<Void> triggerSOS(
            @PathVariable Long stationId,
            @PathVariable Long userId) {
        var station = stationService.getStationById(stationId);
        sosService.handleSOSAlert(station, userId);
        return ResponseEntity.ok().build();
    }
}