package com.gb.spring.web.core.converters;




import com.gb.spring.web.core.entities.Product;
import com.gb.web.api.core.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {


    public Product dtoInEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityInDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }
}
