package com.example.backend.service.impl;

import com.example.backend.dto.AuthResponse;
import com.example.backend.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponse login(String username, String password) {
        // TODO: tu logika weryfikacji + generowanie JWT
        return new AuthResponse("dummy-token");
    }

    @Override
    public void register(String username, String password) {
        // TODO: tu logika zapisu nowego użytkownika
    }
}
