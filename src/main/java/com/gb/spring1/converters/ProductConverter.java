package com.gb.spring1.converters;


import com.gb.spring1.dto.ProductDto;
import com.gb.spring1.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {


    public Product dtoInEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice(), productDto.getManufacturer());
    }

    public ProductDto entityInDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getManufacturer());
    }
}
