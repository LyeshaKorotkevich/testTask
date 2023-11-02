package ru.clevertec.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    /**
     * Маппит DTO в продукт без UUID
     *
     * @param productDto - DTO для маппинга
     * @return новый продукт
     */
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "created", ignore = true)
    Product toProduct(ProductDto productDto);

    /**
     * Маппит текущий продукт в DTO без даты
     *
     * @param product - существующий продукт
     * @return DTO с идентификатором
     */

    InfoProductDto toInfoProductDto(Product product);

    /**
     * Маппит лист продукт в лист DTO
     *
     * @param products - лист продуктов
     * @return List<InfoProductDto>
     */
    List<InfoProductDto> toInfoProductDtoList(List<Product> products);

    /**
     * Сливает существующий продукт с информацией из DTO
     * не меняет дату создания и идентификатор
     *
     * @param product    существующий продукт
     * @param productDto информация для обновления
     * @return обновлённый продукт
     */
    @Mapping(target = "uuid", source = "product.uuid")
    @Mapping(target = "name", source = "productDto.name")
    @Mapping(target = "description", source = "productDto.description")
    @Mapping(target = "price", source = "productDto.price")
    @Mapping(target = "created", source = "product.created")
    Product merge(Product product, ProductDto productDto);
}
