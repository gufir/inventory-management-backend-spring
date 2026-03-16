package com.example.inventory.service;

import java.util.List;
import java.util.UUID;

import com.example.inventory.dto.CreateItemRequest;
import com.example.inventory.dto.ItemResponse;
import com.example.inventory.dto.UpdateItemRequest;

public interface ItemService {
    ItemResponse createItem(CreateItemRequest request);
    ItemResponse updateItem(UUID itemId, UpdateItemRequest request);

    void deleteItem(UUID itemId);
    ItemResponse findbyId(UUID itemId);

    List<ItemResponse>findAllItem();

}
