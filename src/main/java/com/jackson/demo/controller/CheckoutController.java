package com.jackson.demo.controller;
import java.util.UUID;

import com.jackson.demo.dto.request.CheckoutSessionPayRequest;
import com.jackson.demo.dto.request.CreateCheckoutSessionRequest;
import com.jackson.demo.dto.request.FinalizeCheckoutSessionRequest;
import com.jackson.demo.dto.response.CheckoutSessionPayResponse;
import com.jackson.demo.dto.response.CheckoutSessionResponse;
import com.jackson.demo.dto.response.FinalizeCheckoutSessionResponse;
import com.jackson.demo.exception.BadRequestException;
import com.jackson.demo.security.AuthenticatedUser;
import com.jackson.demo.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/checkout/sessions")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @Operation(summary = "Create short-lived checkout session (does not create order)")
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<CheckoutSessionResponse> createSession(
            @Valid @RequestBody(required = false) CreateCheckoutSessionRequest request,
            Authentication authentication) {
        UUID customerId = resolveCustomerId(authentication, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.createSession(customerId, request));
    }

    @Operation(summary = "Process payment against checkout session")
    @PreAuthorize("hasRole('ADMIN') or @accessControlService.canAccessCheckoutSession(#sessionId, authentication)")
    @PostMapping("/{sessionId}/pay")
    public ResponseEntity<CheckoutSessionPayResponse> paySession(
            @PathVariable UUID sessionId,
            @Valid @RequestBody CheckoutSessionPayRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(checkoutService.paySession(sessionId, request));
    }

    @Operation(summary = "Finalize approved checkout session and create order")
    @PreAuthorize("hasRole('ADMIN') or @accessControlService.canAccessCheckoutSession(#sessionId, authentication)")
    @PostMapping("/{sessionId}/finalize")
    public FinalizeCheckoutSessionResponse finalizeSession(
            @PathVariable UUID sessionId,
            @Valid @RequestBody FinalizeCheckoutSessionRequest request) {
        return checkoutService.finalizeSession(sessionId, request);
    }

    @Operation(summary = "Get checkout session details")
    @PreAuthorize("hasRole('ADMIN') or @accessControlService.canAccessCheckoutSession(#sessionId, authentication)")
    @GetMapping("/{sessionId}")
    public CheckoutSessionResponse getSession(@PathVariable UUID sessionId) {
        return checkoutService.getSession(sessionId);
    }

    private UUID resolveCustomerId(Authentication authentication, CreateCheckoutSessionRequest request) {
        if (authentication != null && authentication.getPrincipal() instanceof AuthenticatedUser user) {
            if (user.getCustomerId() != null) {
                return user.getCustomerId();
            }
        }
        if (request != null && request.customerId() != null) {
            return request.customerId();
        }
        throw new BadRequestException("customerId is required for this authenticated user");
    }
}
