package ru.clevertec.product.service.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.exception.ProductNotFoundException;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Override
    public InfoProductDto get(UUID uuid) {
        InfoProductDto infoProductDto = null;
        try {
            Product product = productRepository.findById(uuid).orElseThrow();
            infoProductDto = mapper.toInfoProductDto(product);
        } catch (Exception e) {
            throw new ProductNotFoundException(uuid);
        }
        return infoProductDto;
    }

    @Override
    public List<InfoProductDto> getAll() {
        return mapper.toInfoProductDtoList(productRepository.findAll());
    }

    @Override
    public UUID create(ProductDto productDto) {
        Product product = mapper.toProduct(productDto);
        return productRepository.save(product).getUuid();
    }

    @Override
    public void update(UUID uuid, ProductDto productDto) {
        try {
            Product productToUpdate = productRepository.findById(uuid).orElseThrow();
            Product product = mapper.merge(productToUpdate, productDto);

            productRepository.save(product);
        } catch (Exception e) {
            throw new ProductNotFoundException(uuid);
        }
    }

    @Override
    public void delete(UUID uuid) {
        productRepository.delete(uuid);
    }
}
