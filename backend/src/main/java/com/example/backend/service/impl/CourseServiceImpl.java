package com.example.backend.service.impl;

import com.example.backend.dto.CourseDto;
import com.example.backend.model.Course;
import com.example.backend.repository.CourseRepository;
import com.example.backend.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repo;
    private final ModelMapper mapper;

    public CourseServiceImpl(CourseRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<CourseDto> findAll() {
        return repo.findAll().stream()
                .map(c -> mapper.map(c, CourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto findById(Long id) {
        Course course = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return mapper.map(course, CourseDto.class);
    }

    @Override
    public CourseDto create(CourseDto dto) {
        Course course = mapper.map(dto, Course.class);
        Course saved = repo.save(course);
        return mapper.map(saved, CourseDto.class);
    }

    @Override
    public CourseDto update(Long id, CourseDto dto) {
        Course existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        mapper.map(dto, existing); // nadpisuje pola
        Course updated = repo.save(existing);
        return mapper.map(updated, CourseDto.class);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
