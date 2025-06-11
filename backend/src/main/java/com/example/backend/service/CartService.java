package com.example.backend.repository;

import com.example.backend.dto.CartDto;

public interface CartService {
    CartDto getCurrentCart();
    CartDto addItem(Long courseId, int quantity);
    CartDto removeItem(Long itemId);
    void clearCart();
}
