package com.balai.inventory.controller;

import com.balai.inventory.model.entity.Category;
import com.balai.inventory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/name")
    public ResponseEntity<Optional<Category>> getAllCategoriesByName(String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }
}
