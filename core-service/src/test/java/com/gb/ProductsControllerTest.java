package com.gb;

import com.gb.spring.web.core.SpringWebApplication;
import com.gb.spring.web.core.entities.Product;
import com.gb.spring.web.core.repositories.ProductsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = SpringWebApplication.class)
@AutoConfigureMockMvc
class ProductsControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductsRepository productsRepository;

    @Test
    void findProductByIdTest() throws Exception {

        Product product = new Product();
        product.setId(1L);
        product.setTitle("TV");
        product.setPrice(BigDecimal.valueOf(100));

        given(productsRepository.findById(1L)).willReturn(Optional.of(product));




        mvc.perform(get("/api/v1/products/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", is(product.getTitle())));







    }
    }