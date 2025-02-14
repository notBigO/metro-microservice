package com.metroservice.metro.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SOSAlert {
    private Long userId;
    private String stationName;
    private String managerEmail;
}
