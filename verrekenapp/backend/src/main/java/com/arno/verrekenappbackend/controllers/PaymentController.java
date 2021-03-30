package com.arno.verrekenappbackend.controllers;

import com.arno.verrekenappbackend.models.Addition;
import com.arno.verrekenappbackend.models.Payment;
import com.arno.verrekenappbackend.models.TotalPayment;
import com.arno.verrekenappbackend.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("payments/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String paymentId) {
        Optional<Payment> optionalPayment = paymentService.findPaymentById(Long.parseLong(paymentId));
        return optionalPayment.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PostMapping("payments/add")
    public ResponseEntity<Payment> addPayment(@RequestBody Addition addition) {
        Payment createdPayment = new Payment(addition.getName(), addition.getAmount());
        paymentService.createOrUpdatePayment(createdPayment);
        return ResponseEntity.ok(createdPayment);
    }

    @GetMapping("payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("total")
    public ResponseEntity<Double> getTotal() {
        System.out.println(paymentService.createTotalPayment(paymentService.getAllPayments()));
        return ResponseEntity.ok(paymentService.getTotalAmount());
    }

    @PostMapping("total-payment")
    public ResponseEntity<TotalPayment> getTotalPayment(@RequestBody List<Payment> payments) {
        return ResponseEntity.ok(paymentService.createTotalPayment(payments));
    }
}
