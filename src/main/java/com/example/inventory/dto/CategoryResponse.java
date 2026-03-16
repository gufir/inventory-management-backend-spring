package com.example.inventory.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class CategoryResponse {
    private UUID categoryId;
    private String name;
    
    private Instant createdAt;
    private UUID createdBy;
    
    private Instant updatedAt;
    private UUID updatedBy;
}
