package ru.clevertec.product.exception;

public class ProductNullException extends RuntimeException {
    /**
     * Сообщение должно быть именно такого формата
     */
    public ProductNullException() {
        super("Product is null");
    }
}
