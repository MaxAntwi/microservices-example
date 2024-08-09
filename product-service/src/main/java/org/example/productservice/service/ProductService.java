package org.example.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.ProductRequest;
import org.example.productservice.dto.ProductResponse;
import org.example.productservice.entity.Product;
import org.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void createProducts(ProductRequest productRequest) {
        Product product = Product.builder()
                .description(productRequest.getProductDescription())
                .price(productRequest.getProductPrice())
                .name(productRequest.getProductName()).build();
        productRepository.save(product);
        log.info("Product {} created", product.getId());
        log.info("Product description: {}", productRequest.getProductDescription());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse
                .builder()
                .id(product.getId())
                .productPrice(product.getPrice())
                .productDescription(product.getName())
                .productName(product.getName())
                .build();
    }
}
