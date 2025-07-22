package com.balai.inventory.service;

import com.balai.inventory.exception.ResourceNotFoundException;
import com.balai.inventory.mapper.ProductMapper;
import com.balai.inventory.model.dto.ProductDto;
import com.balai.inventory.model.entity.Category;
import com.balai.inventory.model.entity.Product;
import com.balai.inventory.repository.CategoryRepository;
import com.balai.inventory.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return Optional.ofNullable(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id)));
    }

    public Optional<Product> getProductByName(String name) {
        return Optional.ofNullable(productRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name " + name)));
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByQuantityGreaterThan(0);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameOrDescriptionContaining(keyword);
    }

    @Transactional
    public ProductDto createProduct(ProductDto productDto, MultipartFile image) throws IOException {
        // Map request to entity
        Product product = productMapper.toEntity(productDto);

        // Check if request contains image
        if (image!=null && !image.isEmpty()) {
            String fileName = saveImage(image);
            product.setImage("/images/"+fileName);
        }

        // Handle category creation/lookup
        String categoryName = productDto.getCategoryDto().getName();
        Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(categoryName);
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto, MultipartFile image) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());

        // Check if image request is not empty
        if (image!=null && !image.isEmpty()) {
            // Delete old image file if it exists
            String oldImagePath = existingProduct.getImage(); // e.g., "/images/old-image.jpg"
            if (oldImagePath != null && !oldImagePath.isBlank()) {
                File oldImageFile = new File("src/main/resources/static" + oldImagePath);
                if (oldImageFile.exists()) {
                    oldImageFile.delete();
                }
            }
            String fileName = saveImage(image);
            existingProduct.setImage("/images/"+fileName);
        }
        // Persist updated product into DB and return it as ProductDto object
        Product updatedProduct =  productRepository.save(existingProduct);
        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) throws Exception {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
    }

    public String saveImage(MultipartFile image) throws IOException {
        // Set the fileName to be saved, set the path to save
        String fileName = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "-" + image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR+fileName);
        Files.createDirectories(path.getParent());

        // Write the file into local storage
        Files.write(path, image.getBytes());
        return fileName;
    }
}
