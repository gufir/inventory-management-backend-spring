package com.example.inventory.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.CategoryResponse;
import com.example.inventory.dto.CreateCategoryRequest;
import com.example.inventory.dto.UpdateCategoryRequest;
import com.example.inventory.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    // Find All
    @GetMapping
    public List<CategoryResponse> findAllCategory() {
        return categoryService.findAllCategory();
    }
    

    //Create Category
    @PostMapping
    public CategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }


    //Find By Id
    @GetMapping("/{categoryId}")
    public CategoryResponse findById(@PathVariable UUID categoryId) {
        return categoryService.findbyId(categoryId);
    }

    //Update Category
    @PutMapping("/{categoryId}")
    public CategoryResponse updateCategory(@PathVariable UUID categoryId, @Valid @RequestBody UpdateCategoryRequest request) {
        return categoryService.updateCategory(categoryId, request);
    }

    //Delete Category
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);;
    }
    
    
}
