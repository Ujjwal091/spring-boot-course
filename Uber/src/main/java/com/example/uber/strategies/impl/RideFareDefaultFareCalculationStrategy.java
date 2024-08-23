package com.example.uber.strategies.impl;

import com.example.uber.dto.RideRequestDto;
import com.example.uber.strategies.RideFareCalculationStrategy;

public class RideFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {
    @Override
    public double calculateFare(RideRequestDto rideRequestDto) {
        return 0;
    }
}
