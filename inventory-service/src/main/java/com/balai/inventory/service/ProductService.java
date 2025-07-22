package com.balai.inventory.service;

import com.balai.inventory.exception.ResourceNotFoundException;
import com.balai.inventory.model.entity.Category;
import com.balai.inventory.model.entity.Product;
import com.balai.inventory.repository.CategoryRepository;
import com.balai.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

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

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setCategory(productDetails.getCategory());
        product.setImage(productDetails.getImage());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) throws Exception {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
    }
}
