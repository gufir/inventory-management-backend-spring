package com.example.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryRequest {
    
    @NotBlank(message = "Name is required")
    private String name;

}
