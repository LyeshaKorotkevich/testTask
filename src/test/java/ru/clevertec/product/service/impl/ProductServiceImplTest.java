package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;
import ru.clevertec.product.util.ProductTestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidCaptor;

    @Test
    void getShouldReturnInfoProduct() {
        // given
        UUID uuid = UUID.fromString("e2f53c51-0f2d-4d66-b3fc-e59624aa8cf6");

        InfoProductDto expected = ProductTestData.builder().build().buildInfoProductDto();
        Product productToFind = ProductTestData.builder().build().buildProduct();

        doReturn(Optional.of(productToFind))
                .when(productRepository)
                .findById(uuid);

        doReturn(expected)
                .when(productMapper)
                .toInfoProductDto(productToFind);

        // when
        InfoProductDto actual = productService.get(uuid);

        // then
        verify(productRepository).findById(uuidCaptor.capture());
        assertEquals(uuid, uuidCaptor.getValue());

        assertEquals(expected, actual);

    }

    @Test
    void getAllShouldReturnInfoProductDtos(){
        // given
        List<Product> products = List.of(ProductTestData.builder().build().buildProduct());
        List<InfoProductDto> expected = List.of(ProductTestData.builder().build().buildInfoProductDto());

        doReturn(products)
                .when(productRepository)
                .findAll();

        doReturn(expected)
                .when(productMapper)
                .toInfoProductDtoList(products);

        // when
        List<InfoProductDto> actual = productService.getAll();

        // then
        verify(productRepository).findAll();
        verify(productMapper).toInfoProductDtoList(products);

        assertEquals(expected, actual);
    }

    @Test
    void createShouldReturnUuid() {
        // given
        Product productToSave = ProductTestData.builder()
                .withUuid(null)
                .build().buildProduct();
        Product expected = ProductTestData.builder().build().buildProduct();
        ProductDto productDto = ProductTestData.builder().build().buildProductDto();

        doReturn(productToSave)
                .when(productMapper)
                .toProduct(productDto);

        doReturn(expected)
                .when(productRepository)
                .save(productToSave);

        // when
        productService.create(productDto);

        // then
        verify(productRepository).save(productCaptor.capture());
        Product actual = productCaptor.getValue();

        assertNull(actual.getUuid());
    }

    @Test
    void updateProductWithNewNameAndDescription() {
        // given
        Product productToUpdate = ProductTestData.builder().build().buildProduct();
        UUID uuid = ProductTestData.builder().build().buildProduct().getUuid();
        ProductDto productDtoForUpdate = ProductTestData.builder()
                .withName("New")
                .withDescription("New")
                .build().buildProductDto();
        Product expected = ProductTestData.builder()
                .withName("New")
                .withDescription("New")
                .build().buildProduct();

        when(productRepository.findById(uuid))
                .thenReturn(Optional.of(productToUpdate));

        doReturn(expected)
                .when(productMapper)
                .merge(productToUpdate, productDtoForUpdate);

        doReturn(expected)
                .when(productRepository)
                .save(expected);

        // when
        productService.update(uuid, productDtoForUpdate);

        // then
        verify(productRepository)
                .save(productCaptor.capture());
        Product actual = productCaptor.getValue();
        assertThat(actual)
                .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
                .hasFieldOrPropertyWithValue(Product.Fields.description, expected.getDescription());
    }

    @Test
    void deleteShouldInvokeOneTime() {
        //given
        UUID uuid = UUID.fromString("2f13e8b8-362c-4cd7-a3cf-6b86c42e91d1");

        //when
        productService.delete(uuid);

        //then
        verify(productRepository).delete(uuid);
    }
}
