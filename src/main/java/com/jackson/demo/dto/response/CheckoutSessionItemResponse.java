package com.jackson.demo.dto.response;
import java.util.UUID;

import java.math.BigDecimal;

public record CheckoutSessionItemResponse(
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal) {
}
