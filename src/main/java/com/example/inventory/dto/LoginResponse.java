package com.example.inventory.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private String token;
    private UUID userId;
    private String username;
    private String role;
    private Instant createdAt;
    private Instant updatedAt;
} 
