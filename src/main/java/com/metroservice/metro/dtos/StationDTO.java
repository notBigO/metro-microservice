package com.metroservice.metro.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StationDTO {
    private Long id;

    @NotBlank(message = "Station name is required")
    private String name;

    @NotBlank(message = "Station location is required")
    private String location;

    private boolean active;
}
