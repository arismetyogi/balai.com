package com.balai.inventory.repository;

import com.balai.inventory.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);

    Optional<Category> findByNameIgnoreCase(String categoryName);
}
