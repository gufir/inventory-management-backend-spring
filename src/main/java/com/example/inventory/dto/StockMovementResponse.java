package com.example.inventory.dto;

import java.time.Instant;
import java.util.UUID;

import com.example.inventory.entity.StockMovementType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StockMovementResponse {

    private UUID movementId;
    private UUID itemId;
    private UUID supplierId;
    private StockMovementType type;
    private Integer quantity;
    private Instant createdAt;
    private UUID createdBy;
    private Instant UpdatedAt;
    private UUID updatedBy;

}