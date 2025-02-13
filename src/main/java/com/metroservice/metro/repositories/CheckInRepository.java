package com.metroservice.metro.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metroservice.metro.entities.CheckIn;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    List<CheckIn> findByUserIdAndActiveTrue(String userId);

    Optional<CheckIn> findByTicketIdAndActiveTrue(String ticketId);
}
