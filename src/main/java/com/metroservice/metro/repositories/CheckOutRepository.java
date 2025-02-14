package com.metroservice.metro.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metroservice.metro.entities.CheckOut;

public interface CheckOutRepository extends JpaRepository<CheckOut, Long> {
    List<CheckOut> findByCheckIn_UserId(Long userId);
}