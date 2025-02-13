package com.metroservice.metro.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metroservice.metro.dtos.CheckOutDTO;
import com.metroservice.metro.entities.CheckOut;
import com.metroservice.metro.services.CheckOutService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/check-out")
@RequiredArgsConstructor
public class CheckOutController {
    private final CheckOutService checkOutService;

    @PostMapping
    public ResponseEntity<CheckOut> checkOut(@Valid @RequestBody CheckOutDTO checkOutDTO) {
        return ResponseEntity.ok(checkOutService.processCheckOut(checkOutDTO));
    }
}
