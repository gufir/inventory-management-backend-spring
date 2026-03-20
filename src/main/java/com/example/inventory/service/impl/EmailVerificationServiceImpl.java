package com.example.inventory.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.service.EmailService;
import com.example.inventory.service.EmailVerificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class EmailVerificationServiceImpl implements EmailVerificationService {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final EmailService emailService;

    private static final String PREFIX = "verify";
    private static final long TTL_MINUTES = 15; 

    @Override
    public void sendVerificationEmail(User user) {
        String token = UUID.randomUUID().toString();
        
        redisTemplate.opsForValue().set(
                PREFIX + token,
                user.getUserId().toString(),
                Duration.ofMinutes(TTL_MINUTES)
        );

        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;

        emailService.sendVerificationEmail(
            user.getEmail(), 
            user.getUsername(), 
            verificationLink
        );

    }

    @Override
    public void verifyEmail(String token) {
        
        String key = PREFIX + token;

        String userIdStr = redisTemplate.opsForValue().get(key);

        if (userIdStr == null) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        User user = userRepository.findById(UUID.fromString(userIdStr))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        user.setVerified(true);
        user.setUpdatedAt(Instant.now());
        user.setUpdatedBy(user.getUserId());
        
        userRepository.save(user);

        redisTemplate.delete(key);
    }

}
