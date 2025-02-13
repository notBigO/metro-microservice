package com.metroservice.metro.services;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveUserService {
    private final RedisTemplate<String, Object> redisTemplate;

    public void addActiveUser(String userId, Long stationId) {
        String redisKey = "active-users" + stationId;
        redisTemplate.opsForSet().add(redisKey, userId);
        log.info("Added user {} to active users for station {}", userId, stationId);
    }

    public void removeActiveUser(String userId, Long stationId) {
        String redisKey = "active-users" + stationId;
        redisTemplate.opsForSet().remove(redisKey, userId);
        log.info("User {} removed from active users at station {}", userId, stationId);
    }

    public Set<Object> getActiveUsers(Long stationId) {
        String redisKey = "active-users" + stationId;
        return redisTemplate.opsForSet().members(redisKey);
    }
}
