package com.arno.verrekenappbackend.repositories;

import com.arno.verrekenappbackend.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
