package com.jackson.demo.dto.response;
import java.util.UUID;

import com.jackson.demo.model.PaymentStatus;

public record CheckoutSessionPayResponse(
        UUID checkoutSessionId,
        UUID paymentTransactionId,
        PaymentStatus status,
        String gatewayResponseCode,
        String gatewayMessage) {
}
