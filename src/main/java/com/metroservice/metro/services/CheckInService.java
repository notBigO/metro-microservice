package com.metroservice.metro.services;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Check;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metroservice.metro.dtos.CheckInDTO;
import com.metroservice.metro.entities.CheckIn;
import com.metroservice.metro.entities.Station;
import com.metroservice.metro.exceptions.MetroException;
import com.metroservice.metro.repositories.CheckInRepository;
import com.metroservice.metro.repositories.StationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckInRepository checkInRepository;
    private final StationRepository stationRepository;
    private final ActiveUserService activeUserService;

    public List<CheckIn> getAllActiveCheckIns(String userId) {
        return checkInRepository.findByUserIdAndActiveTrue(userId);
    }

    @Transactional
    public CheckIn processCheckIn(CheckInDTO checkInDTO) {
        log.debug("Procesing checkin for user: {}", checkInDTO.getUserId());

        Station station = stationRepository.findById(checkInDTO.getStationId())
                .orElseThrow(() -> new MetroException("Station not found", HttpStatus.NOT_FOUND));

        // try to seee if user has any active checkins
        checkInRepository.findByUserIdAndActiveTrue(checkInDTO.getUserId())
                .stream()
                .findFirst()
                .ifPresent(activeCheckIn -> {
                    throw new MetroException("User has an active check in already", HttpStatus.BAD_REQUEST);
                });

        CheckIn checkIn = new CheckIn();
        checkIn.setUserId(checkInDTO.getUserId());
        checkIn.setStation(station);
        checkIn.setCheckInTime(LocalDateTime.now());
        checkIn.setTicketType(checkInDTO.getTicketType());
        checkIn.setTicketId(checkInDTO.getTicketId());
        checkIn.setActive(true);

        activeUserService.addActiveUser(checkInDTO.getUserId(), station.getId());

        log.debug("Checkin created: {}", checkIn);
        return checkInRepository.save(checkIn);
    }
}
