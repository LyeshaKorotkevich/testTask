package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    ProductService productService;

    @Test
    void delete() {
        //given
        UUID uuid = UUID.fromString("");

        //when
        productService.delete(uuid);

        //then
        verify(productRepository).delete(uuid);
    }
}
