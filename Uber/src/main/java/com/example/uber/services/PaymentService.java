package com.example.uber.services;

import com.example.uber.entities.Payment;
import com.example.uber.entities.Ride;
import com.example.uber.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
