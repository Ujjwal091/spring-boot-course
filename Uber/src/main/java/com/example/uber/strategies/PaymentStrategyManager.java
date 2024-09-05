package com.example.uber.strategies;

import com.example.uber.entities.enums.PaymentMethod;
import com.example.uber.strategies.impl.PaymentCashStrategy;
import com.example.uber.strategies.impl.PaymentWalletStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final PaymentWalletStrategy walletPaymentStrategy;
    private final PaymentCashStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
