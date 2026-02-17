package com.product.oauthintegration.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
