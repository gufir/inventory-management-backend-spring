package com.example.inventory.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.inventory.dto.CreateItemRequest;
import com.example.inventory.dto.ItemResponse;
import com.example.inventory.entity.Item;
import com.example.inventory.repository.ItemRepository;
import com.example.inventory.security.CurrentUser;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    
    @Mock
    private CurrentUser currentUser;

    @InjectMocks
    private ItemServiceImpl itemService;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
    }

    @Test
    void testCreateItemSuccess() {
        // Implement test for creating an item successfully
        CreateItemRequest request = new CreateItemRequest();
        request.setName("Laptop");
        when(currentUser.getUserId()).thenReturn(userId);
        when(itemRepository.existsByItemNameAndDeletedAtIsNull("Laptop")).thenReturn(false);

       ItemResponse response = itemService.createItem(request);
       assertNotNull(response);
       assertEquals("Laptop", response.getName());

       verify(itemRepository, times(1)).save(any(Item.class));
    }

}
