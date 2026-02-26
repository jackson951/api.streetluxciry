package com.jackson.demo.repository;
import java.util.UUID;

import com.jackson.demo.entity.CheckoutSession;
import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CheckoutSessionRepository extends JpaRepository<CheckoutSession, UUID> {
    Optional<CheckoutSession> findByIdAndCustomerId(UUID id, UUID customerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from CheckoutSession s where s.id = :id and s.customer.id = :customerId")
    Optional<CheckoutSession> findByIdAndCustomerIdForUpdate(
            @Param("id") UUID id, @Param("customerId") UUID customerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from CheckoutSession s where s.id = :id")
    Optional<CheckoutSession> findByIdForUpdate(@Param("id") UUID id);
}
