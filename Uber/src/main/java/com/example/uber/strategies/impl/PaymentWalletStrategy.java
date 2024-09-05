package com.example.uber.strategies.impl;

import com.example.uber.entities.Driver;
import com.example.uber.entities.Payment;
import com.example.uber.entities.Rider;
import com.example.uber.entities.enums.PaymentStatus;
import com.example.uber.entities.enums.TransactionMethod;
import com.example.uber.repositories.PaymentRepository;
import com.example.uber.services.WalletService;
import com.example.uber.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentWalletStrategy implements PaymentStrategy {


    private final WalletService walletService;
    private final PaymentRepository paymentRepository;


    @Transactional
    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);

        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),
                driversCut, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
