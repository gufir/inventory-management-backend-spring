package com.example.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateSupplierRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    // @NotBlank(message = "Email is required")
    // @Email(message = "Email should be valid")
    // private String email;

    @NotBlank(message = "Phone Number is required")
    private String phone;
}
