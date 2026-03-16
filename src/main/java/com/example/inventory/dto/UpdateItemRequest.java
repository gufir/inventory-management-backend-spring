package com.example.inventory.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateItemRequest {
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String itemCode;
    
    @Min(value = 0, message = "Stock must be non-negative")
    private Integer stock;

    private UUID categoryId;

}
