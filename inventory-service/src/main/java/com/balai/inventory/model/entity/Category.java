package com.balai.inventory.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

public class Category extends BaseEntity{
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Product product;
}
