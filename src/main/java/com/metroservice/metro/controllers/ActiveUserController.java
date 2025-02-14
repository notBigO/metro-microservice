package com.metroservice.metro.controllers;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroservice.metro.services.ActiveUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/active-users")
@RequiredArgsConstructor
public class ActiveUserController {
    private final ActiveUserService activeUserService;

    @GetMapping("/{stationId}")
    public ResponseEntity<Set<Object>> getActiveUsers(@PathVariable Long stationId) {
        Set<Object> activeUsers = activeUserService.getActiveUsers(stationId);
        return ResponseEntity.ok(activeUsers);
    }
}
