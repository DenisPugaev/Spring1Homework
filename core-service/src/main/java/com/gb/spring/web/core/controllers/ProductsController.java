package com.gb.spring.web.core.controllers;


import com.gb.spring.web.core.converters.ProductConverter;

import com.gb.spring.web.core.entities.Product;
import com.gb.spring.web.core.repositories.ProductsRepository;
import com.gb.spring.web.core.services.ProductService;
import com.gb.spring.web.core.validators.ProductValidator;
import com.gb.web.api.core.ProductDto;
import com.gb.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsRepository productRepository;


    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;


    @GetMapping
    public Page<ProductDto> findAllProduct(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
            @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        if (page < 1) {
            page = 1;
        }
        Page<ProductDto> productDtoPage = productService.findAll(minPrice, maxPrice, titlePart, page).map(productConverter::entityInDto);
        log.debug(productDtoPage.toString());
        return productDtoPage;
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityInDto(product);
    }


    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        Product product = productService.update(productDto);
        return productConverter.entityInDto(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }


}
