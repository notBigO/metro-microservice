package com.metroservice.metro.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "station_managers")
public class StationManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    private boolean active = true;
}