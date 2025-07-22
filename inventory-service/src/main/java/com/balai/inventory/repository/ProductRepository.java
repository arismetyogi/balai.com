package com.balai.inventory.repository;

import com.balai.inventory.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByCategoryName(String categoryName);

    List<Product> findByQuantityGreaterThan(int i);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description LIKE %?1%")
    List<Product> findByNameOrDescriptionContaining(String keyword);
}
