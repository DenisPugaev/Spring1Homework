package com.gb.spring1.controllers;


import com.gb.spring1.converters.ProductConverter;
import com.gb.spring1.dto.ProductDto;
import com.gb.spring1.entities.Product;
import com.gb.spring1.exceptions.ResourceNotFoundException;
import com.gb.spring1.services.ProductService;
import com.gb.spring1.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {


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
        log.debug(String.format("%nLogParam - Page: %s%n minPrice: %f%n maxPrice: %f%n namePart: %s%n", page, minPrice, maxPrice, titlePart));
        if (page < 1) {
            page = 1;
        }
        log.info(productService.findAll(minPrice, maxPrice, titlePart, page).map(productConverter::entityInDto).toString());
        return productService.findAll(minPrice, maxPrice, titlePart, page).map(productConverter::entityInDto);
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
