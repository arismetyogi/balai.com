package com.balai.inventory.service;

import com.balai.inventory.exception.ResourceNotFoundException;
import com.balai.inventory.model.entity.Category;
import com.balai.inventory.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name " + name)));
    }
}
