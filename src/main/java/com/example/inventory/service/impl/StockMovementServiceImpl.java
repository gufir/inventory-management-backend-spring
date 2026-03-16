package com.example.inventory.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventory.dto.CreateStockMovementRequest;
import com.example.inventory.dto.StockMovementResponse;
import com.example.inventory.entity.Item;
import com.example.inventory.entity.StockMovement;
import com.example.inventory.entity.StockMovementType;
import com.example.inventory.entity.Supplier;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.repository.StockMovementRepository;
import com.example.inventory.repository.SupplierRepository;
import com.example.inventory.service.StockMovementService;
import com.example.inventory.security.CurrentUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ItemRepository itemRepository;
    private final SupplierRepository supplierRepository;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    public StockMovementResponse createMovement(CreateStockMovementRequest request){

        UUID userId = currentUser.getUserId();

        Item item = itemRepository.findByItemIdAndDeletedAtIsNull(request.getItemId())
            .orElseThrow();

        Supplier supplier = supplierRepository.findBySupplierIdAndDeletedAtIsNull(request.getSupplierId())
            .orElseThrow();

        if(request.getType() == StockMovementType.OUT) {
            if(item.getStock() < request.getQuantity()) {
                throw new RuntimeException("Stock not enough");
            }

            item.setStock(item.getStock() - request.getQuantity());

        }

        if(request.getType() == StockMovementType.IN) {
            item.setStock(item.getStock()+request.getQuantity());
        }

        StockMovement movement = new StockMovement();
        movement.setMovementId(UUID.randomUUID());
        movement.setItem(item);
        movement.setSupplier(supplier);
        movement.setType(request.getType());
        movement.setQuantity(request.getQuantity());
        movement.setCreatedAt(Instant.now());
        movement.setCreatedBy(userId);
        movement.setUpdatedAt(Instant.now());
        movement.setUpdatedBy(userId);

        stockMovementRepository.save(movement);
        itemRepository.save(item);

        return mapToResponse(movement);
    }

    @Override
    public List<StockMovementResponse> getMovementByItem(UUID itemId) {
        List<StockMovement> movement = stockMovementRepository.findByItem_ItemIdAndDeletedAtIsNull(itemId);

    return movement.stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Override
    @Transactional
    public void deleteMovement(UUID movementId) {
        UUID userId = currentUser.getUserId();
        StockMovement movement = stockMovementRepository.findById(movementId).orElseThrow(() -> new IllegalArgumentException("movement not found"));

        movement.setDeletedAt(Instant.now());
        movement.setDeletedBy(userId);

    }

    private StockMovementResponse mapToResponse(StockMovement movement) {
        return StockMovementResponse.builder()
            .movementId(movement.getMovementId())
            .itemId(movement.getItem().getItemId())
            .supplierId(movement.getSupplier().getSupplierId())
            .type(movement.getType())
            .quantity(movement.getQuantity())
            .createdAt(movement.getCreatedAt())
            .createdBy(movement.getCreatedBy())
            .UpdatedAt(movement.getUpdatedAt())
            .updatedBy(movement.getUpdatedBy())
            .build();
    }


}
