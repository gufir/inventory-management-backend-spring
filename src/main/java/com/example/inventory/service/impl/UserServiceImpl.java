package com.example.inventory.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.inventory.dto.CreateUserRequest;
import com.example.inventory.dto.LoginRequest;
import com.example.inventory.dto.LoginResponse;
import com.example.inventory.dto.UpdateUserRequest;
import com.example.inventory.dto.UserResponse;
import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import com.example.inventory.security.JwtService;
import com.example.inventory.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        UUID currentUserId = getCurrentUserId();

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        
        user.setCreatedAt(Instant.now());
        user.setCreatedBy(currentUserId);
        user.setUpdatedAt(Instant.now());
        user.setUpdatedBy(currentUserId);

        userRepository.save(user);

        return mapToUserResponse(user);

    }

    @Override
    public UserResponse update(UUID userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        // Username change check
        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException("Username already exists");
            }
            user.setUsername(request.getUsername());
        }

        // Email change check
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }

        // Password Update
        if (request.getPassword() != null) {
            user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        user.setUpdatedAt(Instant.now());
        user.setUpdatedBy(getCurrentUserId());

        return mapToUserResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getHashedPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtService.generateToken(user.getUserId(), user.getUsername(), user.getRole());

        return LoginResponse.builder()
        .token(token)
        .userId(user.getUserId())
        .username(user.getUsername())
        .role(user.getRole())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt()).build();
    }

    @Override
    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setDeletedAt(Instant.now());
        user.setDeletedBy(getCurrentUserId());
    }

    @Override
    public UserResponse findbyId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapToUserResponse(user);
    }

    private UUID getCurrentUserId() {
        return UUID.randomUUID();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
