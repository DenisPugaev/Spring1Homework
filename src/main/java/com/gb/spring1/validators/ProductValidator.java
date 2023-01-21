package com.gb.spring1.validators;


import com.gb.spring1.dto.ProductDto;
import com.gb.spring1.exceptions.ValidationException;
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
        if (productDto.getManufacturer().isBlank()) {
            errors.add("Название производителя неможет быть пустым!");
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
