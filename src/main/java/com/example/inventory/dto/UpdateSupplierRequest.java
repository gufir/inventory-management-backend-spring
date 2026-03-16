package com.example.inventory.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSupplierRequest {
    private String name;
    private String address;
    @Email(message = "Email should be valid")
    private String email;
    private String phone;
}
