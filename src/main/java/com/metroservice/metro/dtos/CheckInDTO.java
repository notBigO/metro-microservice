package com.metroservice.metro.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckInDTO {
    @NotBlank(message = "User ID is required")
    private String userId;

    @NotNull(message = "Station ID is required")
    private Long stationId;

    @NotBlank(message = "Ticket type is required")
    private String ticketType;

    private String ticketId;
}
