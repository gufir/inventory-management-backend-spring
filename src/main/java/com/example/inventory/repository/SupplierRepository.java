package com.example.inventory.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory.entity.Supplier;


@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    
    Optional<Supplier> findBySupplierIdAndDeletedAtIsNull(UUID supplierId);
    List<Supplier> findByDeletedAtIsNull();
    boolean existsBySupplierName(String supplierName);
    boolean existsBySupplierId(UUID supplierId);
}
