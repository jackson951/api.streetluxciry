package com.jackson.demo.dto.response;
import java.util.UUID;

import com.jackson.demo.model.CheckoutSessionStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record CheckoutSessionResponse(
        UUID checkoutSessionId,
        CheckoutSessionStatus status,
        BigDecimal amount,
        String currency,
        Instant expiresAt,
        String clientPaymentToken,
        List<CheckoutSessionItemResponse> items) {
}
