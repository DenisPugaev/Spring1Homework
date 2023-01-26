package com.gb.spring1.services;


import com.gb.spring1.aspect.annotation.Timer;
import com.gb.spring1.dto.ProductDto;
import com.gb.spring1.entities.Product;
import com.gb.spring1.exceptions.ResourceNotFoundException;
import com.gb.spring1.repository.ProductRepository;
import com.gb.spring1.repository.specifications.ProductSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Timer
    public Page<Product> findAll(BigDecimal minPrice, BigDecimal maxPrice, String titlePart, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (titlePart != null) {
            spec = spec.and(ProductSpecifications.nameLike(titlePart));
        }




        return productRepository.findAll(spec, PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "Id")));
    }


    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


    @Transactional
    public Product update(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукт! ID:" + productDto.getId() + " не найден!"));
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setManufacturer(product.getManufacturer());
        return product;
    }
}
