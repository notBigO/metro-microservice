package com.metroservice.metro.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroservice.metro.dtos.StationManagerDTO;
import com.metroservice.metro.services.StationManagerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/station-managers")
@RequiredArgsConstructor
public class StationManagerController {
    private final StationManagerService stationManagerService;

    @PostMapping
    public ResponseEntity<StationManagerDTO> addManager(@RequestBody StationManagerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stationManagerService.addStationManager(dto));
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<List<StationManagerDTO>> getManagers(@PathVariable Long stationId) {
        return ResponseEntity.ok(stationManagerService.getManagersByStation(stationId));
    }
}