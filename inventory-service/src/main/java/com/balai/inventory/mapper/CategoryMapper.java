package com.balai.inventory.mapper;

import com.balai.inventory.model.dto.CategoryDto;
import com.balai.inventory.model.dto.ProductDto;
import com.balai.inventory.model.entity.Category;
import com.balai.inventory.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "productId", source = "product-id") //add mapping
    CategoryDto toDto(Category product);

    @Mapping(target = "product-id", source = "productId") //add mapping
    Category toEntity(CategoryDto productDto);
}
