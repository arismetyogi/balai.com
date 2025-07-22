package com.balai.inventory.repository;

import com.balai.inventory.model.entity.Category;
import com.balai.inventory.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByCategoryId(Category categoryId);
    List<Product> findByCategoryName(Category categoryName);
}
