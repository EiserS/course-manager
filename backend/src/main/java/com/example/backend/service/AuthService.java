package com.example.backend.repository;

import com.example.backend.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password);
    void register(String username, String password);
}
