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

    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    public ResponseEntity<Product> getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<Product> getProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with name " + name));
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<List<Product>> getAvailableProducts() {
        return ResponseEntity.ok(productRepository.findByQuantityGreaterThan(0));
    }

    public ResponseEntity<List<Product>> getProductsByCategoryId(Long categoryId) {
        return ResponseEntity.ok(productRepository.findByCategoryId(categoryId));
    }

    public ResponseEntity<List<Product>> getProductsByCategoryName(String categoryName) {
        return ResponseEntity.ok(productRepository.findByCategoryName(categoryName));
    }

    public ResponseEntity<List<Product>> searchProducts(String keyword) {
        return ResponseEntity.ok(productRepository.findByNameOrDescriptionContaining(keyword));
    }

    public ResponseEntity<Product> createProduct(Product product) {
        Product newProduct = productRepository.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    public ResponseEntity<Product> updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setCategory(productDetails.getCategory());
        product.setImage(productDetails.getImage());

        return ResponseEntity.ok(productRepository.save(product));
    }

    public ResponseEntity<?> deleteProduct(Long id) throws Exception {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
        return ResponseEntity.ok("Product has been deleted");
    }

    public ResponseEntity<?> updateProductQuantity(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (product.getQuantity() >= quantity) {
                product.setQuantity(product.getQuantity() - quantity);
                productRepository.save(product);
                return ResponseEntity.ok(product);
            }
        }
        return new ResponseEntity<>(new ResourceNotFoundException("Product not found with ID " + productId), HttpStatus.NOT_FOUND);
    }
}
