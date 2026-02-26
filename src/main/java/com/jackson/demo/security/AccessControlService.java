package com.jackson.demo.security;
import java.util.UUID;

import com.jackson.demo.entity.CheckoutSession;
import com.jackson.demo.entity.CustomerOrder;
import com.jackson.demo.repository.CheckoutSessionRepository;
import com.jackson.demo.repository.CustomerOrderRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("accessControlService")
public class AccessControlService {

    private final CustomerOrderRepository customerOrderRepository;
    private final CheckoutSessionRepository checkoutSessionRepository;

    public AccessControlService(
            CustomerOrderRepository customerOrderRepository,
            CheckoutSessionRepository checkoutSessionRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.checkoutSessionRepository = checkoutSessionRepository;
    }

    public boolean canAccessCustomer(UUID customerId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if (isAdmin(authentication)) {
            return true;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthenticatedUser user) {
            return user.getCustomerId() != null && user.getCustomerId().equals(customerId);
        }
        return false;
    }

    @SuppressWarnings("null")
    public boolean canAccessOrder(UUID orderId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if (isAdmin(authentication)) {
            return true;
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AuthenticatedUser user) || user.getCustomerId() == null) {
            return false;
        }
        return customerOrderRepository.findById(orderId)
                .map(CustomerOrder::getCustomer)
                .map(customer -> customer.getId().equals(user.getCustomerId()))
                .orElse(false);
    }

    @SuppressWarnings("null")
    public boolean canAccessCheckoutSession(UUID checkoutSessionId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        if (isAdmin(authentication)) {
            return true;
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AuthenticatedUser user) || user.getCustomerId() == null) {
            return false;
        }
        return checkoutSessionRepository.findById(checkoutSessionId)
                .map(CheckoutSession::getCustomer)
                .map(customer -> customer.getId().equals(user.getCustomerId()))
                .orElse(false);
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }
}
