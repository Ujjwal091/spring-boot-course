package com.example.uber.strategies.impl;

import com.example.uber.entities.Driver;
import com.example.uber.entities.Payment;
import com.example.uber.entities.Wallet;
import com.example.uber.entities.enums.PaymentStatus;
import com.example.uber.entities.enums.TransactionMethod;
import com.example.uber.repositories.PaymentRepository;
import com.example.uber.services.WalletService;
import com.example.uber.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentCashStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);

    }
}
