package com.example.inventory.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.dto.CreateUserRequest;
import com.example.inventory.dto.LoginRequest;
import com.example.inventory.dto.LoginResponse;
import com.example.inventory.dto.RefreshTokenRequest;
import com.example.inventory.dto.RefreshTokenResponse;
import com.example.inventory.dto.UserResponse;
import com.example.inventory.security.JwtService;
import com.example.inventory.service.EmailVerificationService;
import com.example.inventory.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;
    private final JwtService jwt;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        emailVerificationService.verifyEmail(token);
        return "Email verified successfully";
    }

    @PostMapping("/resend-verification")
    public String resendVerificationEmail(@RequestParam String email) {

        var user = userService.findEntityByEmail(email);

        if (user.isVerified()) {
            throw new IllegalArgumentException("Email already verified");
        }

        emailVerificationService.sendVerificationEmail(user);

        return "Verification email resent successfully";
    }   

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request ) {
        return jwt.refreshToken(request);
    }


    
}
