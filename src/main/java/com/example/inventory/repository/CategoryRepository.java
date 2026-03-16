package com.example.inventory.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryIdAndDeletedAtIsNull(UUID categoryId);
    
    List<Category> findByDeletedAtIsNull();

    boolean existsByCategoryName(String categoryName);

}
