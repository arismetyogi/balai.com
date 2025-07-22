package com.balai.inventory.controller;

import com.balai.inventory.model.dto.ProductDto;
import com.balai.inventory.model.entity.Product;
import com.balai.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(this.productService.getAllProducts());
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping({"/name/{name}"})
    public ResponseEntity<Optional<Product>> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @GetMapping({"/available"})
    public ResponseEntity<List<Product>> getAvailableProducts() {
        return ResponseEntity.ok(this.productService.getAvailableProducts());
    }

    @GetMapping({"/category/{category}"})
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(this.productService.getProductsByCategoryName(category));
    }

    @GetMapping({"/search"})
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(this.productService.searchProducts(keyword));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product, MultipartFile image) throws IOException {
        return new ResponseEntity<>(this.productService.createProduct(product, image), HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto, MultipartFile image) throws IOException {
        return ResponseEntity.ok(this.productService.updateProduct(id, productDto, image));
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws Exception {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
