package com.metroservice.metro.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroservice.metro.dtos.CheckInDTO;
import com.metroservice.metro.entities.CheckIn;
import com.metroservice.metro.services.CheckInService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/check-in")
@RequiredArgsConstructor
public class CheckInController {
    private final CheckInService checkInService;

    @PostMapping
    public ResponseEntity<CheckIn> checkIn(@Valid @RequestBody CheckInDTO checkInDTO) {
        return ResponseEntity.ok(checkInService.processCheckIn(checkInDTO));
    }
}
