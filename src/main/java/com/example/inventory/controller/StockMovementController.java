package com.example.inventory.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inventory.dto.CreateStockMovementRequest;
import com.example.inventory.dto.StockMovementResponse;
import com.example.inventory.service.StockMovementService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService movementService;

    @PostMapping
    public ResponseEntity<StockMovementResponse> createMovement(
            @Valid @RequestBody CreateStockMovementRequest request) {

        StockMovementResponse response =
                movementService.createMovement(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<StockMovementResponse>> getMovementsByItem(
            @PathVariable UUID itemId) {

        List<StockMovementResponse> response =
                movementService.getMovementByItem(itemId);

        return ResponseEntity.ok(response);
    }

    
    @DeleteMapping("/{movementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovement(@PathVariable UUID movementId) {
        movementService.deleteMovement(movementId);;
    }
}