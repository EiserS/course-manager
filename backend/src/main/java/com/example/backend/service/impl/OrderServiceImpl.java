package com.example.backend.service.impl;

import com.example.backend.dto.OrderDto;
import com.example.backend.model.Order;
import com.example.backend.model.OrderStatus;
import com.example.backend.repository.OrderRepository;
import com.example.backend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final ModelMapper mapper;

    public OrderServiceImpl(OrderRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<OrderDto> findAll() {
        return repo.findAll().stream()
                .map(o -> mapper.map(o, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto findById(Long id) {
        Order order = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto checkout() {
        // TODO: logika przeniesienia koszyka do zamówienia
        Order newOrder = new Order();
        // ... wypełnij pola, zapisz repo.save(newOrder)
        Order saved = repo.save(newOrder);
        return mapper.map(saved, OrderDto.class);
    }

    @Override
    public void cancel(Long orderId) {
        repo.findById(orderId).ifPresent(o -> {
            o.setStatus(OrderStatus.CANCELED);
            repo.save(o);
        });
    }

}
