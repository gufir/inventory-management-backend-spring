package com.example.inventory.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter

public class UpdateUserRequest {
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    private String password;
    private String role;
}
