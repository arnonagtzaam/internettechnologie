package com.arno.verrekenappbackend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TotalPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payments_id")
    private long paymentsId;

    @OneToMany(mappedBy = "totalPayment")
    @JsonManagedReference
    private List<Loan> loans;

    private LocalDate date;

    public TotalPayment(LocalDate date) {
        this.loans = new ArrayList<>();
        this.date = date;
    }

    public TotalPayment() {
    }

    public Long getId() {
        return paymentsId;
    }

    public void setId(Long id) {
        this.paymentsId = id;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TotalPayment{" +
                "id=" + paymentsId +
                ", loans=" + loans +
                ", date=" + date +
                '}';
    }
}
