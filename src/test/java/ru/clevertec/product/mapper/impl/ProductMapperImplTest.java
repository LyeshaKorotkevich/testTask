package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Test;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.mapper.ProductMapperImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperImplTest {

    private ProductMapper productMapper = new ProductMapperImpl();

    @Test
    void toProductShouldReturnProduct() {
        // given
        ProductDto productDto = new ProductDto("Cup", "cup", BigDecimal.valueOf(3));
        Product expected = new Product(null, "Cup", "cup", BigDecimal.valueOf(3), null);

        // when
        Product actual = productMapper.toProduct(productDto);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
    }

    @Test
    void toInfoProductDtoShouldReturnInfoProduct() {
        // given
        Product product = new Product(UUID.fromString("5b2bf998-09b1-4e7a-8b5e-83962ef46147"),
                "Mug", "Mug", BigDecimal.valueOf(4), null);
        InfoProductDto expected = new InfoProductDto(UUID.fromString("5b2bf998-09b1-4e7a-8b5e-83962ef46147"),
                "Mug", "Mug", BigDecimal.valueOf(4));

        // when
        InfoProductDto actual = productMapper.toInfoProductDto(product);

        // then
        assertEquals(expected, actual);
    }

    @Test
    void mergeShouldReturnProduct() {
        // given
        Product product = new Product(UUID.fromString("5b2bf998-09b1-4e7a-8b5e-83962ef46147"),
                "Bag", "Bag", BigDecimal.valueOf(20), LocalDateTime.MAX);
        ProductDto productDto = new ProductDto("Bag", "Bag", BigDecimal.valueOf(20));
        Product expected = new Product(UUID.fromString("5b2bf998-09b1-4e7a-8b5e-83962ef46147"),
                "Bag", "Bag", BigDecimal.valueOf(20), LocalDateTime.MAX);

        // when
        Product actual = productMapper.merge(product, productDto);

        // then
        assertEquals(expected, actual);
    }
}