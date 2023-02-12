package com.gb.spring.web.core.services;



import com.gb.spring.web.core.dto.ProductDto;
import com.gb.spring.web.core.entities.Product;
import com.gb.spring.web.core.repositories.ProductsRepository;
import com.gb.spring.web.core.repositories.specifications.ProductsSpecifications;
import com.gb.web.api.exceptions.ResourceNotFoundException;
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
    private final ProductsRepository productRepository;

    public ProductService(ProductsRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Page<Product> findAll(BigDecimal minPrice, BigDecimal maxPrice, String titlePart, Integer page) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (titlePart != null) {
            spec = spec.and(ProductsSpecifications.titleLike(titlePart));
        }




        return productRepository.findAll(spec, PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.ASC, "Id")));
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
