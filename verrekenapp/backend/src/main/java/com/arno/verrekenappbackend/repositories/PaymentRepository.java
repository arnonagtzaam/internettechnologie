package com.arno.verrekenappbackend.repositories;

import com.arno.verrekenappbackend.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
