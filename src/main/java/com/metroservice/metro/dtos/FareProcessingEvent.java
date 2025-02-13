package com.metroservice.metro.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FareProcessingEvent {
    private String userId;
    private String ticketId;
    private double fare;
    private LocalDateTime checkOutTime;
}
