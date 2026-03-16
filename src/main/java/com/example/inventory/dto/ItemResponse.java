package com.example.inventory.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class ItemResponse {
    private UUID itemId;
    private String name;
    private String itemCode;
    private Integer stock;
    private UUID categoryId;

    private Instant createdAt;
    private UUID createdBy;

    private Instant updatedAt;
    private UUID updatedBy;
}
