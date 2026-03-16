package com.example.inventory.dto;

import java.util.UUID;

import com.example.inventory.entity.StockMovementType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStockMovementRequest {
    @NotNull
    private UUID ItemId;

    @NotNull
    private UUID supplierId;

    @NotNull
    private StockMovementType type;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;

}
