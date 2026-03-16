package com.example.inventory.service;

import java.util.List;
import java.util.UUID;

import com.example.inventory.dto.CreateStockMovementRequest;
import com.example.inventory.dto.StockMovementResponse;

public interface StockMovementService {
    StockMovementResponse createMovement(CreateStockMovementRequest request);
    List<StockMovementResponse> getMovementByItem(UUID itemId);

    void deleteMovement(UUID movementId);
}
