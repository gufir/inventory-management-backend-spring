package com.example.inventory.dto;

import java.time.Instant;
import java.util.UUID;

import com.example.inventory.entity.RoleType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class UserResponse {
    private UUID userId;
    private String username;
    private String email;
    private RoleType role;

    private Instant createdAt;
    private Instant updatedAt;

    private Boolean isVerified;
    
}
