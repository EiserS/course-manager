package com.example.backend.service.impl;

import com.example.backend.dto.CartDto;
import com.example.backend.dto.CartItemDto;
import com.example.backend.model.Cart;
import com.example.backend.model.CartItem;
import com.example.backend.model.Course;
import com.example.backend.model.User;
import com.example.backend.repository.CartRepository;
import com.example.backend.repository.CourseRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepo;
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper;

    public CartServiceImpl(CartRepository cartRepo,
                           CourseRepository courseRepo,
                           UserRepository userRepo,
                           ModelMapper mapper) {
        this.cartRepo = cartRepo;
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @Override
    public CartDto getCurrentCart() {
        // załóżmy, że mamy usera zalogowanego o id=1L
        User user = userRepo.findById(1L)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepo.save(c);
                });
        return mapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto addItem(Long courseId, int quantity) {
        CartDto dto = getCurrentCart();
        Cart cart = mapper.map(dto, Cart.class);

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setCourse(course);
        item.setQuantity(quantity);
        cart.getItems().add(item);

        Cart saved = cartRepo.save(cart);
        return mapper.map(saved, CartDto.class);
    }

    @Override
    public CartDto removeItem(Long itemId) {
        CartDto dto = getCurrentCart();
        Cart cart = mapper.map(dto, Cart.class);
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        Cart saved = cartRepo.save(cart);
        return mapper.map(saved, CartDto.class);
    }

    @Override
    public void clearCart() {
        CartDto dto = getCurrentCart();
        Cart cart = mapper.map(dto, Cart.class);
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
