package com.example.inventory.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    Optional<Item> findByItemIdAndDeletedAtIsNull(UUID itemId);

    Optional<Item> findByItemCodeAndDeletedAtIsNull(String itemCode);
    
    List<Item> findByDeletedAtIsNull();

    boolean existsByItemCodeAndDeletedAtIsNull(String itemCode);

    boolean existsByItemCodeAndDeletedAtIsNullAndItemIdNot(String itemCode, UUID itemId);
    
    boolean existsByItemNameAndDeletedAtIsNull(String itemName);

    boolean existsByItemIdAndDeletedAtIsNull(UUID itemId);
}
