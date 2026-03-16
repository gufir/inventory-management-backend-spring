package com.example.inventory.service;

import java.util.List;
import java.util.UUID;

import com.example.inventory.dto.CategoryResponse;
import com.example.inventory.dto.CreateCategoryRequest;
import com.example.inventory.dto.UpdateCategoryRequest;

public interface CategoryService {

    CategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse updateCategory(UUID categoryId, UpdateCategoryRequest request);

    void deleteCategory(UUID categoryId);
    CategoryResponse findbyId(UUID categoryId);

    List<CategoryResponse> findAllCategory();
}
