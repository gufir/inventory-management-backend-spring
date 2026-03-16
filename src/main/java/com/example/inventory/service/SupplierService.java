package com.example.inventory.service;

import java.util.List;
import java.util.UUID;

import com.example.inventory.dto.CreateSupplierRequest;
import com.example.inventory.dto.SupplierResponse;
import com.example.inventory.dto.UpdateSupplierRequest;

public interface SupplierService {
    SupplierResponse createSupplier(CreateSupplierRequest request);
    SupplierResponse updateSupplier(UUID supplierId, UpdateSupplierRequest request);
    void deleteSupplier(UUID supplierId);
    SupplierResponse findbyId(UUID supplierID);
    List<SupplierResponse> findAllSupplier();

}
