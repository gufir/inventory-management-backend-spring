package com.example.inventory.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SupplierResponse {
    private UUID supplierId;
    private String supplier_name;
    private String address;
    // private String email;
    private String phone;

    private Instant createdAt;
    private UUID createdBy;

    private Instant updatedAt;
    private UUID updatedBy;
}
