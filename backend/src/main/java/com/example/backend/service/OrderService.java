package com.example.backend.repository;

import com.example.backend.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    OrderDto findById(Long id);
    OrderDto checkout();          // <-- nowa metoda
    void cancel(Long orderId);    // <-- nowa metoda
}
