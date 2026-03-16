package com.example.inventory.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.inventory.dto.CategoryResponse;
import com.example.inventory.dto.CreateCategoryRequest;
import com.example.inventory.dto.UpdateCategoryRequest;
import com.example.inventory.entity.Category;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.security.CurrentUser;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    
    @Mock
    private CurrentUser currentUser;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
    }

    @Test
    void testCreateCategorySuccess() {

        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Electronics");

        when(currentUser.getUserId()).thenReturn(userId);
        when(categoryRepository.existsByCategoryName("Electronics")).thenReturn(false);

        CategoryResponse response = categoryService.createCategory(request);

        assertNotNull(response);
        assertEquals("Electronics", response.getName());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testCreateCategoryAlreadyExists() {

        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Electronics");

        when(categoryRepository.existsByCategoryName("Electronics")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.createCategory(request);
        });

        assertEquals("Category Name already exist", exception.getMessage());
    }

    @Test
    void testUpdateCategorySuccess() {

        UUID categoryId = UUID.randomUUID();

        UpdateCategoryRequest request = new UpdateCategoryRequest();
        request.setName("Updated Category");

        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName("Old Name");

        when(currentUser.getUserId()).thenReturn(userId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryResponse response = categoryService.updateCategory(categoryId, request);

        assertEquals("Updated Category", response.getName());

        verify(categoryRepository).save(category);
    }

    @Test
    void testDeleteCategorySuccess() {

        UUID categoryId = UUID.randomUUID();

        Category category = new Category();
        category.setCategoryId(categoryId);

        when(currentUser.getUserId()).thenReturn(userId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(categoryId);

        assertNotNull(category.getDeletedAt());
        assertEquals(userId, category.getDeletedBy());
    }

    @Test
    void testFindByIdSuccess() {

        UUID categoryId = UUID.randomUUID();

        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName("Electronics");

        when(categoryRepository.findByCategoryIdAndDeletedAtIsNull(categoryId))
                .thenReturn(Optional.of(category));

        CategoryResponse response = categoryService.findbyId(categoryId);

        assertEquals("Electronics", response.getName());
    }
}
