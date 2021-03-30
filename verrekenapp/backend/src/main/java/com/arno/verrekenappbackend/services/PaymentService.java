package com.arno.verrekenappbackend.services;

import com.arno.verrekenappbackend.models.Loan;
import com.arno.verrekenappbackend.models.Payment;
import com.arno.verrekenappbackend.models.TotalPayment;
import com.arno.verrekenappbackend.repositories.LoanRepository;
import com.arno.verrekenappbackend.repositories.TotalPaymentRepository;
import com.arno.verrekenappbackend.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private LoanRepository loanRepository;
    private TotalPaymentRepository totalPaymentRepository;

    public PaymentService(PaymentRepository paymentRepository, LoanRepository loanRepository, TotalPaymentRepository totalPaymentRepository) {
        this.paymentRepository = paymentRepository;
        this.loanRepository = loanRepository;
        this.totalPaymentRepository = totalPaymentRepository;
    }

    public Optional<Payment> findPaymentById(long id) {
        return paymentRepository.findById(id);
    }

    public Optional<Loan> findLoanById(long id) {
        return loanRepository.findById(id);
    }

    public Optional<TotalPayment> findTotalPaymentById(long id) {
        return totalPaymentRepository.findById(id);
    }

    public void createOrUpdatePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public void createOrUpdateLoan(Loan loan) {
        loanRepository.save(loan);
    }

    public void createOrUpdateTotalPayment(TotalPayment totalPayment) {
        totalPaymentRepository.save(totalPayment);
    }

    public List<Payment> getAllPayments() {
        return StreamSupport.stream(paymentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public TotalPayment createTotalPayment(List<Payment> payments) {
        TotalPayment totalPayment = new TotalPayment(LocalDate.now());
        List<Loan> loans = new ArrayList<>();
        Map<String, Double> amountPerPerson = new HashMap<>();
        double totalExpenses = 0;

        for (Payment payment : payments) {
            if(amountPerPerson.containsKey(payment.getName())) {
                amountPerPerson.put(payment.getName(), amountPerPerson.get(payment.getName()) + payment.getAmount());
            } else {
                amountPerPerson.put(payment.getName(), payment.getAmount());
            }
            totalExpenses += payment.getAmount();
        }

        int totalAmountOfPersons = amountPerPerson.keySet().size();
        double averageCosts = totalExpenses / totalAmountOfPersons;

        for (String name : amountPerPerson.keySet()) {
            amountPerPerson.put(name, amountPerPerson.get(name) - averageCosts);
        }

        List<Double> values = new ArrayList<>(amountPerPerson.values());
        Collections.sort(values);

        while (Collections.frequency(values, 0.0) != values.size()) {
            String fromPerson = getKey(amountPerPerson,values.get(0));
            String toPerson = getKey(amountPerPerson,values.get(totalAmountOfPersons - 1));
            double amount = -values.get(0);

            if(amount <= 0.01) {
                break;
            }

            amountPerPerson.put(toPerson, amountPerPerson.get(toPerson) - amount);
            amountPerPerson.put(fromPerson, 0.0);

            loans.add(new Loan(fromPerson, toPerson, round(amount,2)));

            values = new ArrayList<>(amountPerPerson.values());
            Collections.sort(values);
        }
        totalPayment.setLoans(loans);
        totalPayment.setDate(LocalDate.now());
        createOrUpdateTotalPayment(totalPayment);

        for(Loan loan : loans) {
            loan.setTotalPayment(totalPayment);
            createOrUpdateLoan(loan);
        }

        return totalPayment;
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (K key: map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }

    public Loan createLoan(Payment from, Payment to, double amount, TotalPayment totalPayment) {
        Loan loan = new Loan(from.getName(), to.getName(), amount);
        loan.setTotalPayment(totalPayment);
        totalPayment.addLoan(loan);
        createOrUpdateLoan(loan);
        createOrUpdateTotalPayment(totalPayment);
        return loan;
    }

    public double getTotalAmount() {
        double total = 0;
        for (Payment payment : getAllPayments()) {
            total += payment.getAmount();
        }
        return round(total, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
