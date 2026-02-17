package com.product.oauthintegration.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Product name is required.")
        String name,

        @Size(max = 500, message = "Product description should be less than 500 characters.")
        String description,

        @NotNull(message = "Product price is required.")
        @DecimalMin(value = "0.00", message = "Price should be more than 0.")
        BigDecimal price,

        @NotNull(message = "Product quantity is required")
        Integer quantity
) {
}
