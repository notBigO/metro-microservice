package com.metroservice.metro.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroservice.metro.dtos.RouteDTO;
import com.metroservice.metro.services.RouteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<List<RouteDTO>> getAllActiveRoutes() {
        return ResponseEntity.ok(routeService.getAllActiveRoutes());
    }

    @PostMapping
    public ResponseEntity<RouteDTO> createRoute(@Valid @RequestBody RouteDTO routeDTO) {
        return ResponseEntity.ok(routeService.createRoute(routeDTO));
    }
}
