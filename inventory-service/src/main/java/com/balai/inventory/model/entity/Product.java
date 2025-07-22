package com.balai.inventory.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Auditable{
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
