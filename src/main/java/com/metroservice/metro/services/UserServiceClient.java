package com.metroservice.metro.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.metroservice.metro.dtos.MetroCardDTO;
import com.metroservice.metro.dtos.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceClient {
    private final WebClient userServiceWebClient;

    public Mono<UserDTO> getUser(Long userId) {
        return userServiceWebClient.get()
                .uri("/api/v1/users/{userId}", userId)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .doOnError(error -> log.error("Error fetching user: {}", error.getMessage()));
    }

    public Mono<MetroCardDTO> getMetroCard(Long userId, String cardNumber) {
        return userServiceWebClient.get()
                .uri("/api/v1/users/{userId}/cards/{cardNumber}", userId, cardNumber)
                .retrieve()
                .bodyToMono(MetroCardDTO.class)
                .doOnError(error -> log.error("Error fetching metro card: {}", error.getMessage()));
    }
}