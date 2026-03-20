package com.example.inventory.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.inventory.dto.CategoryResponse;
import com.example.inventory.dto.CreateCategoryRequest;
import com.example.inventory.dto.UpdateCategoryRequest;
import com.example.inventory.entity.Category;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.security.CurrentUser;
import com.example.inventory.service.CategoryService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    
    private final CategoryRepository categoryRepository;
    private final CurrentUser currentUser;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        UUID userId = currentUser.getUserId();

        if (categoryRepository.existsByCategoryName(request.getName())){
            throw new RuntimeException("Category Name already exist");
        }

        Category category = new Category();
        category.setCategoryId(UUID.randomUUID());
        category.setCategoryName(request.getName());
        category.setCreatedBy(userId);
        category.setCreatedAt(Instant.now());
        category.setUpdatedAt(Instant.now());
        category.setUpdatedBy(userId);

        categoryRepository.save(category);

        return mapToResponse(category);

    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest request) {
        UUID userId = currentUser.getUserId();

        Category category = categoryRepository.findById(categoryId)
            .filter(c -> c.getDeletedAt() == null)
            .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setCategoryName(request.getName());
        category.setUpdatedAt(Instant.now());
        category.setUpdatedBy(userId);

        categoryRepository.save(category);
        return mapToResponse(category);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(UUID categoryId) {
        UUID userId = currentUser.getUserId();
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        category.setDeletedAt(Instant.now());
        category.setDeletedBy(userId);
        
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findbyId(UUID categoryId) {
        Category category = categoryRepository.findByCategoryIdAndDeletedAtIsNull(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> findAllCategory() {

    List<Category> categories = categoryRepository.findByDeletedAtIsNull();

    return categories.stream()
            .map(this::mapToResponse)
            .toList();
    
    }


    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
            .categoryId(category.getCategoryId())
            .name(category.getCategoryName())
            .createdAt(category.getCreatedAt())
            .createdBy(category.getCreatedBy())
            .updatedAt(category.getUpdatedAt())
            .updatedBy(category.getUpdatedBy())
            .build();
    }


}
