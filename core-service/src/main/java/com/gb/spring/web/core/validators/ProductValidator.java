package com.gb.spring.web.core.validators;




import com.gb.spring.web.core.exceptions.ValidationException;
import com.gb.web.api.core.ProductDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {

    public void validate(ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        if (productDto.getPrice().compareTo(BigDecimal.ONE) <= 0) {
            errors.add("Неверно установлена цена продукта! Цена не может быть меньше 1!");
        }
        if (productDto.getTitle().isBlank()) {
            errors.add("Имя продукта не может быть путстым!");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
