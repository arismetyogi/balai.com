package com.balai.inventory.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String image;

    @JsonProperty
    private CategoryDto categoryDto;
}
