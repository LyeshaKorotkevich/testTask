package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryProductRepositoryTest {
    public ProductRepository productRepository = new InMemoryProductRepository();

    @Nested
    class FindByID {
        @Test
        void findByIdShouldReturnProduct() {
            //given
            UUID uuid = UUID.fromString("e2f53c51-0f2d-4d66-b3fc-e59624aa8cf6");
            Product expected = new Product(uuid, "Something", "About something",
                    BigDecimal.valueOf(1), LocalDateTime.of(2023, 11, 18, 0, 0));

            //when
            Product actual = productRepository.findById(uuid).orElseThrow();

            //then
            assertEquals(expected, actual);
        }

        @Test
        void findByIdShouldReturnOptionalEmpty() {
            //given
            UUID uuid = UUID.fromString("e2f53c51-0f2d-4d66-b3fc-e59624aa8cf7");
            Optional<Product> excepted = Optional.empty();

            //when
            Optional<Product> actual = productRepository.findById(uuid);

            //then
            assertEquals(excepted, actual);
        }

        @Test
        void findByIdShouldReturnExpectedProductEqualsWithoutUuid() {
            // given
            UUID uuid = UUID.fromString("e2f53c51-0f2d-4d66-b3fc-e59624aa8cf6");
            Product expected = new Product(uuid, "Something", "About something",
                    BigDecimal.valueOf(1), LocalDateTime.of(2023, 11, 18, 0, 0));

            // when
            Product actual = productRepository.findById(uuid).orElseThrow();

            //then
            assertThat(actual)
                    .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, expected.getCreated());
        }
    }

    @Nested
    class FindAll {
        @Test
        void findAllShouldReturnListOfProducts() {
            //given
            List<Product> expected = List.of(new Product(uuid, "Something", "About something",
                    BigDecimal.valueOf(1), LocalDateTime.of(2023, 11, 18, 0, 0)));

            //when
            List<Product> actual = productRepository.findAll();

            //then
            assertEquals(expected, actual);
        }
    }
}
