package ru.clevertec.product.repository.impl;

import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private final HashMap<UUID, Product> productMap = new HashMap<>();

    @Override
    public Optional<Product> findById(UUID uuid) {
        return productMap
                .entrySet()
                .stream()
                .filter(product -> product.getKey().equals(uuid))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        return productMap
                .values()
                .stream()
                .toList();
    }

    @Override
    public Product save(Product product) {
        if (product == null) throw new IllegalArgumentException();
        productMap.put(product.getUuid(), product);
        return product;
    }

    @Override
    public void delete(UUID uuid) {
        productMap.remove(uuid);
    }
}
