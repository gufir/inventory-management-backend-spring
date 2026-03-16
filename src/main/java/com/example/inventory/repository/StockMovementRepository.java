package com.example.inventory.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory.entity.StockMovement;
import com.example.inventory.entity.StockMovementType;


@Repository

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
    
    Optional<StockMovement> findByMovementIdAndDeletedAtIsNull(UUID movementId);

    List<StockMovement> findByItem_ItemIdAndDeletedAtIsNull(UUID itemId);

    List<StockMovement> findBySupplier_SupplierIdAndDeletedAtIsNull(UUID supplierId);
    List<StockMovement> findByType(StockMovementType type);
    
}
