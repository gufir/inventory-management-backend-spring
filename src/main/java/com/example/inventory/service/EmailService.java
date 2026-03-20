package com.example.inventory.service;

public interface EmailService {
    void sendVerificationEmail(String to, String username, String link);
}
