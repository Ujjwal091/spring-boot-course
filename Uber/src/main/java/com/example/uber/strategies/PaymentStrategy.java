package com.example.uber.strategies;

import com.example.uber.entities.Payment;

public interface PaymentStrategy {
    static final double PLATFORM_COMMISSION = 0.3;

    void processPayment(Payment payment);
}
