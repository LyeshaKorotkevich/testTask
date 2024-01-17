package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.util.ProductTestData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryProductRepositoryTest {

    private ProductRepository productRepository = new InMemoryProductRepository();

    @Nested
    class FindByIDProduct {

        @ParameterizedTest
        @MethodSource("ru.clevertec.product.repository.impl.InMemoryProductRepositoryTest#provideProducts")
        void findByIdShouldReturnProduct(Product product) {
            // given
            Product expected = product;
            productRepository.save(expected);

            // when
            Product actual = productRepository.findById(expected.getUuid()).orElseThrow();

            // then
            assertEquals(expected, actual);
        }

        @Test
        void findByIdShouldReturnOptionalEmpty() {
            // given
            UUID uuid = UUID.fromString("e2f53c51-0f2d-4d66-b3fc-e59624aa8cf7");
            Optional<Product> expected = Optional.empty();

            // when
            Optional<Product> actual = productRepository.findById(uuid);

            // then
            assertEquals(expected, actual);
        }

    }

    @Nested
    class FindAllProducts {

        @Test
        void findAllShouldReturnListOfProducts() {
            // given
            List<Product> expected = List.of(ProductTestData.builder().build().buildProduct(),
                    ProductTestData.builder()
                            .withUuid(UUID.fromString("e2f53c51-0f2d-4d66-b3fc-e59624aa8cf9"))
                            .withName("Anything")
                            .withDescription("About anything")
                            .build().buildProduct()
            );
            expected.stream().forEach(product -> productRepository.save(product));

            // when
            List<Product> actual = productRepository.findAll();

            // then
            assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
        }

        @Test
        void findAllShouldReturnNull() {
            // given

            // when
            List<Product> actual = productRepository.findAll();

            // then
            assertTrue(actual.isEmpty());
        }

    }

    @Nested
    class SaveProduct {

        @ParameterizedTest
        @MethodSource("ru.clevertec.product.repository.impl.InMemoryProductRepositoryTest#provideProducts")
        void saveProductShouldReturnProduct(Product product) {
            // given
            Product expected = product;

            // when
            Product actual = productRepository.save(expected);

            // then
            assertEquals(expected, actual);
        }

        @Test
        void saveNullProductShouldThrowIllegalArgumentException() {
            // given
            Product actual = null;

            // when, then
            assertThrows(IllegalArgumentException.class,
                    () -> productRepository.save(actual));
        }

    }

    @Nested
    class DeleteProduct {
        @ParameterizedTest
        @MethodSource("ru.clevertec.product.repository.impl.InMemoryProductRepositoryTest#provideProducts")
        void deleteProduct(Product product) {
            // given
            Optional<Product> expected = Optional.empty();

            // when
            productRepository.delete(product.getUuid());

            // then
            Optional<Product> actual = productRepository.findById(product.getUuid());
            assertEquals(expected, actual);
        }

    }

    public static Stream<Product> provideProducts() {
        return Stream.of(
                ProductTestData.builder().build().buildProduct(),
                ProductTestData.builder()
                        .withUuid(UUID.fromString("3a5e6b18-02b6-4a52-9f14-07e21e0b8d31"))
                        .withName("PS5")
                        .withDescription("Game console")
                        .withPrice(BigDecimal.valueOf(1500))
                        .build().buildProduct(),
                ProductTestData.builder()
                        .withUuid(UUID.fromString("4f8cbf8b-7218-4dcd-8d8c-4f5c4a3b6dab"))
                        .withName("XBOX ONE")
                        .withDescription("Game console")
                        .withPrice(BigDecimal.valueOf(1000))
                        .build().buildProduct(),
                ProductTestData.builder()
                        .withUuid(UUID.fromString("1a0d65d2-1e0a-4e12-9956-1d98c8c24539"))
                        .build().buildProduct(),
                ProductTestData.builder()
                        .withUuid(UUID.fromString("92e1ac44-7d3b-48c1-96cb-0e96d0e421d9"))
                        .build().buildProduct(),
                ProductTestData.builder()
                        .withUuid(UUID.fromString("4ac4154d-7f07-40f0-9d9b-372634c2a080"))
                        .build().buildProduct()
        );
    }
}
