package com.arno.verrekenappbackend.repositories;

import com.arno.verrekenappbackend.models.TotalPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalPaymentRepository extends JpaRepository<TotalPayment, Long> {
}
