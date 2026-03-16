package com.example.inventory.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventory.dto.CreateSupplierRequest;
import com.example.inventory.dto.SupplierResponse;
import com.example.inventory.dto.UpdateSupplierRequest;
import com.example.inventory.entity.Supplier;
import com.example.inventory.repository.SupplierRepository;
import com.example.inventory.security.CurrentUser;
import com.example.inventory.service.SupplierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final CurrentUser currentUser;

    @Override
    public SupplierResponse createSupplier(CreateSupplierRequest request) {
        UUID userId = currentUser.getUserId();

        if(supplierRepository.existsBySupplierName(request.getName())){
            throw new RuntimeException("Supplier Name already exist");
        }

        Supplier supplier = new Supplier();
        supplier.setSupplierId(UUID.randomUUID());
        supplier.setSupplierName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setPhone(request.getPhone());

        supplier.setCreatedAt(Instant.now());
        supplier.setCreatedBy(userId);
        supplier.setUpdatedAt(Instant.now());
        supplier.setUpdatedBy(userId);

        supplierRepository.save(supplier);
        return mapToResponse(supplier);
    }

    @Override
    @Transactional
    public SupplierResponse updateSupplier(UUID suplierId, UpdateSupplierRequest request) {
        UUID userId = currentUser.getUserId();

        Supplier supplier = supplierRepository.findById(suplierId)
            .filter(c -> c.getDeletedAt() == null)
            .orElseThrow(() -> new RuntimeException("Suplier not found"));

        supplier.setSupplierName(request.getName());
        supplier.setAddress(request.getAddress());
        supplier.setPhone(request.getPhone());

        supplier.setUpdatedAt(Instant.now());
        supplier.setUpdatedBy(userId);

        supplierRepository.save(supplier);

        return mapToResponse(supplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(UUID supplierId) {
        UUID userId = currentUser.getUserId();
        Supplier supplier = supplierRepository.findById(supplierId)
            .orElseThrow(() -> new IllegalArgumentException("supplier not found"));
        supplier.setDeletedAt(Instant.now());
        supplier.setDeletedBy(userId);
        
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponse findbyId(UUID supplierId) {
        Supplier supplier = supplierRepository.findBySupplierIdAndDeletedAtIsNull(supplierId).orElseThrow(() -> new RuntimeException("supplier not found"));

        return mapToResponse(supplier);
    }

    @Override
    public List<SupplierResponse> findAllSupplier() {

    List<Supplier> supplier = supplierRepository.findByDeletedAtIsNull();

    return supplier.stream()
            .map(this::mapToResponse)
            .toList();
    
    }


    private SupplierResponse mapToResponse(Supplier supplier) {
        return SupplierResponse.builder()
            .supplierId(supplier.getSupplierId())
            .supplier_name(supplier.getSupplierName())
            .address(supplier.getAddress())
            .phone(supplier.getPhone())
            .createdAt(supplier.getCreatedAt())
            .createdBy(supplier.getCreatedBy())
            .updatedAt(supplier.getUpdatedAt())
            .updatedBy(supplier.getUpdatedBy())
            .build();
    }

}
