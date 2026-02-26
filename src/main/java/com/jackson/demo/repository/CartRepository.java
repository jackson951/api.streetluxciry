package com.jackson.demo.repository;
import java.util.UUID;

import com.jackson.demo.entity.Cart;
import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCustomerId(UUID customerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Cart c where c.customer.id = :customerId")
    Optional<Cart> findByCustomerIdForUpdate(@Param("customerId") UUID customerId);
}
