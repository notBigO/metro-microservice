package com.metroservice.metro.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckOutDTO {
    @NotNull(message = "Check-in ID is required")
    private Long checkInId;

    @NotNull(message = "Station ID is required")
    private Long stationId;
}