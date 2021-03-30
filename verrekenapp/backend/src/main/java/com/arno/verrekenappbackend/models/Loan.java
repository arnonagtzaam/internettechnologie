package com.arno.verrekenappbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private long loanId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="payments_id")
    private TotalPayment totalPayment;

    private String fromPerson;

    private String toPerson;

    private double amount;

    public Loan(String from, String to, double amount) {
        this.fromPerson = from;
        this.toPerson = to;
        this.amount = amount;
    }

    public Loan() {
    }

    public Long getId() {
        return loanId;
    }

    public void setId(Long id) {
        this.loanId = id;
    }

    public TotalPayment getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(TotalPayment totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getFrom() {
        return fromPerson;
    }

    public void setFrom(String from) {
        this.fromPerson = from;
    }

    public String getTo() {
        return toPerson;
    }

    public void setTo(String to) {
        this.toPerson = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + loanId +
                ", from='" + fromPerson + '\'' +
                ", to='" + toPerson + '\'' +
                ", amount=" + amount +
                '}';
    }
}
