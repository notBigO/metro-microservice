package com.metroservice.metro.dtos;

import java.time.LocalDate;

import com.metroservice.metro.entities.MetroCardStatus;

import lombok.Data;

@Data
public class MetroCardDTO {
    private Long id;
    private String cardNumber;
    private Double balance;
    private LocalDate expirationDate;
    private LocalDate createdAt;
    private MetroCardStatus status;
}
