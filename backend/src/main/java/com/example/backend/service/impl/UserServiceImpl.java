package com.example.backend.service.impl;

import com.example.backend.dto.UserDto;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repo;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> findAll() {
        return repo.findAll().stream()
                .map(u -> mapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto create(UserDto dto) {
        User user = mapper.map(dto, User.class);
        User saved = repo.save(user);
        return mapper.map(saved, UserDto.class);
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        mapper.map(dto, existing);
        User updated = repo.save(existing);
        return mapper.map(updated, UserDto.class);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
