package com.balai.inventory.mapper;

import com.balai.inventory.model.dto.ProductDto;
import com.balai.inventory.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    // Methods to convert Product DTO onject into Product model object and vice-versa
    @Mapping(target = "image", source = "image") //add mapping
    ProductDto toDto(Product product);

    @Mapping(target = "image", source = "image") //add mapping
    @Mapping(target = "category", ignore = true) // handle manually in service
    Product toEntity(ProductDto productDto);

}
