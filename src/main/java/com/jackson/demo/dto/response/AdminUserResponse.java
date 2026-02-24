package com.jackson.demo.dto.response;

import java.time.Instant;
import java.util.Set;

public record AdminUserResponse(
        Long id,
        String email,
        String fullName,
        Set<String> roles,
        boolean enabled,
        Long customerId,
        Instant createdAt) {
}
