package com.jackson.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FinalizeCheckoutSessionRequest(
        @NotBlank @Size(max = 120) String idempotencyKey) {
}
