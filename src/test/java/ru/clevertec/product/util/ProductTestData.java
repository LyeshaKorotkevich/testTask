package ru.clevertec.product.util;


import lombok.Builder;
import lombok.Data;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class ProductTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("e2f53c51-0f2d-4d66-b3fc-e59624aa8cf6");

    @Builder.Default
    private String name = "Something";

    @Builder.Default
    private String description = "About something";

    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(1);

    @Builder.Default
    private LocalDateTime created = LocalDateTime.of(2023, 11, 18, 0, 0);

    public Product buildProduct() {
        return Product.builder()
                .uuid(uuid)
                .name(name)
                .description(description)
                .price(price)
                .created(created)
                .build();

    }
}
