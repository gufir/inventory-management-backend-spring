package com.example.inventory.service;

import java.util.UUID;

import com.example.inventory.dto.CreateUserRequest;
import com.example.inventory.dto.LoginRequest;
import com.example.inventory.dto.LoginResponse;
import com.example.inventory.dto.UpdateUserRequest;
import com.example.inventory.dto.UserResponse;
import com.example.inventory.entity.User;

public interface UserService {
    UserResponse create(CreateUserRequest request);
    
    UserResponse update(UUID userId, UpdateUserRequest request);

    LoginResponse login(LoginRequest request);

    void delete(UUID userId);

    UserResponse findById(UUID userId);

    User findEntityByEmail(String email);
}
