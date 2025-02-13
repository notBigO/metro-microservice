package com.metroservice.metro.dtos;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class StationManagerDTO {
    private Long id;

    @NotBlank(message = "Manager name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Station ID is required")
    private Long stationId;

    private boolean active;
}