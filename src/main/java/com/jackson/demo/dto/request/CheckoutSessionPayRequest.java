package com.jackson.demo.dto.request;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CheckoutSessionPayRequest(
        @NotNull UUID paymentMethodId,
        @Size(min = 3, max = 4) String cvv,
        @NotBlank @Size(max = 120) String idempotencyKey) {
}
