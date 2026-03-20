package com.example.inventory.service;

import com.example.inventory.entity.User;

public interface EmailVerificationService {
    void sendVerificationEmail(User user);
    void verifyEmail(String token);
}
