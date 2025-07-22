package com.balai.inventory.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity{
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Product> product;
}
