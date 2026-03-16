package com.example.inventory.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.ItemResponse;
import com.example.inventory.dto.CreateItemRequest;
import com.example.inventory.dto.UpdateItemRequest;
import com.example.inventory.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    //Create Item
    @PostMapping
    public ItemResponse createItem(@Valid @RequestBody CreateItemRequest request) {
        return itemService.createItem(request);
    }

    //Get All Item
    @GetMapping
    public List<ItemResponse> findAllItem() {
        return itemService.findAllItem();
    }
    


    //Find By Id
    @GetMapping("/{itemId}")
    public ItemResponse findById(@PathVariable UUID itemId) {
        return itemService.findbyId(itemId);
    }

    //Update Item
    @PutMapping("/{itemId}")
    public ItemResponse updateItem(@PathVariable UUID itemId, @Valid @RequestBody UpdateItemRequest request) {
        return itemService.updateItem(itemId, request);
    }

    //Delete Item
    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable UUID itemId) {
        itemService.deleteItem(itemId);;
    }
}
