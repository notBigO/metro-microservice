package com.metroservice.metro.services;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;

import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metroservice.metro.dtos.CheckOutDTO;
import com.metroservice.metro.dtos.FareProcessingEvent;
import com.metroservice.metro.entities.CheckIn;
import com.metroservice.metro.entities.CheckOut;
import com.metroservice.metro.entities.Station;
import com.metroservice.metro.exceptions.MetroException;
import com.metroservice.metro.repositories.CheckInRepository;
import com.metroservice.metro.repositories.CheckOutRepository;
import com.metroservice.metro.repositories.StationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckOutService {
    private final CheckInRepository checkInRepository;
    private final CheckOutRepository checkOutRepository;
    private final StationRepository stationRepository;
    private final RouteService routeService;
    private final KafkaProducerService kafkaProducerService;
    private final ActiveUserService activeUserService;

    private static final double BASE_FARE_PER_KM = 5.0;
    private static final double MINIMUM_FARE = 10.0;
    private static final int TIME_LIMIT_MINUTES = 90;
    private static final double PENALTY_RATE_PER_MINUTE = 10.0;

    @Transactional
    public CheckOut processCheckOut(CheckOutDTO checkOutDTO) {
        log.debug("Processing check out for check-in: {}", checkOutDTO.getCheckInId());

        CheckIn checkIn = checkInRepository.findById(checkOutDTO.getCheckInId())
                .orElseThrow(() -> new MetroException("Check-in not found", HttpStatus.NOT_FOUND));

        if (!checkIn.isActive()) {
            throw new MetroException("Check-out is already completed", HttpStatus.BAD_REQUEST);
        }

        Station exitStation = stationRepository.findById(checkOutDTO.getStationId())
                .orElseThrow(() -> new MetroException("Exit station not found", HttpStatus.NOT_FOUND));

        LocalDateTime checkOutTime = LocalDateTime.now();
        long durationInMinutes = ChronoUnit.MINUTES.between(checkIn.getCheckInTime(), checkOutTime);

        double fare = calculateFare(checkIn.getStation(), exitStation, checkIn.getCheckInTime(),
                checkOutTime, durationInMinutes, checkIn.getTicketType());

        CheckOut checkout = new CheckOut();
        checkout.setCheckIn(checkIn);
        checkout.setStation(exitStation);
        checkout.setCheckOutTime(checkOutTime);
        checkout.setFare(fare);

        checkIn.setActive(false);
        checkInRepository.save(checkIn);
        checkOutRepository.save(checkout);

        activeUserService.removeActiveUser(checkIn.getUserId(), checkIn.getStation().getId());

        FareProcessingEvent event = new FareProcessingEvent(
                checkIn.getUserId(),
                checkIn.getTicketId(),
                fare,
                checkOutTime);
        kafkaProducerService.sendFareProcessingEvent(event);

        return checkout;
    }

    private double calculateFare(Station sourceStation, Station exitStation,
            LocalDateTime checkInTime, LocalDateTime checkOutTime,
            long durationInMinutes, String ticketType) {

        double distance = routeService.calculateDistance(sourceStation, exitStation);
        double fare = Math.max(MINIMUM_FARE, distance * BASE_FARE_PER_KM);

        // checkig for peak hour
        int hour = checkOutTime.getHour();
        if ((hour >= 8 && hour < 10) || (hour >= 18 && hour < 20)) {
            fare *= 1.2;
        }

        // penalty if duration exceeds time limit
        if (durationInMinutes > TIME_LIMIT_MINUTES) {
            double penaltyMinutes = durationInMinutes - TIME_LIMIT_MINUTES;
            fare += penaltyMinutes * PENALTY_RATE_PER_MINUTE;
        }

        if ("METRO_CARD".equals(ticketType)) {
            fare *= 0.9;
        }

        return Math.round(fare * 100.0) / 100.0;
    }

}
