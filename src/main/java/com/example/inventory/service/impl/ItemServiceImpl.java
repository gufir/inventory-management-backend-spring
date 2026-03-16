package com.example.inventory.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inventory.dto.CreateItemRequest;
import com.example.inventory.dto.ItemResponse;
import com.example.inventory.dto.UpdateItemRequest;
import com.example.inventory.entity.Category;
import com.example.inventory.entity.Item;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.security.CurrentUser;
import com.example.inventory.service.ItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final CurrentUser currentUser;

    @Override
    public ItemResponse createItem(CreateItemRequest request) {

        UUID userId = currentUser.getUserId();

        if(itemRepository.existsByItemCodeAndDeletedAtIsNull(request.getItemCode())) {
            throw new RuntimeException("Item code already exist");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .filter(c -> c.getDeletedAt() == null)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Item item = new Item();
        item.setItemId(UUID.randomUUID());
        item.setItemName(request.getName());
        item.setItemCode(request.getItemCode());
        item.setStock(request.getStock());
        item.setCategory(category);
        item.setCreatedBy(userId);
        item.setCreatedAt(Instant.now());
        item.setUpdatedBy(userId);
        item.setUpdatedAt(Instant.now());

        itemRepository.save(item);
        return mapToResponse(item);

    }

    @Override
    public ItemResponse updateItem(UUID id, UpdateItemRequest request) {

        UUID userId = currentUser.getUserId();

        Item item = itemRepository.findById(id)
            .filter(i -> i.getDeletedAt() == null)
            .orElseThrow(() -> new RuntimeException("Item not found"));
    
        item.setItemName(request.getName());
        item.setStock(request.getStock());

        item.setUpdatedAt(Instant.now());
        item.setUpdatedBy(userId);

        itemRepository.save(item);
        return mapToResponse(item);
    }

    @Override
    @Transactional
    public void deleteItem(UUID itemId) {

        UUID userId = currentUser.getUserId();
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Item not found"));
    
        item.setDeletedAt(Instant.now());
        item.setDeletedBy(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponse findbyId(UUID itemId) {
        Item item = itemRepository.findByItemIdAndDeletedAtIsNull(itemId).orElseThrow(() -> new RuntimeException("Item not found"));

        return mapToResponse(item);
    }

    @Override
    public List<ItemResponse> findAllItem() {

    List<Item> item = itemRepository.findByDeletedAtIsNull();

    return item.stream()
            .map(this::mapToResponse)
            .toList();
    
    }

    private ItemResponse mapToResponse(Item item) {
        return ItemResponse.builder()
        .itemId(item.getItemId())
        .name(item.getItemName())
        .itemCode(item.getItemCode())
        .stock(item.getStock())
        .categoryId(item.getCategory().getCategoryId())
        .createdAt(item.getCreatedAt())
        .createdBy(item.getCreatedBy())
        .updatedAt(item.getUpdatedAt())
        .updatedBy(item.getUpdatedBy())
        .build();
    }
}
