package com.example.uber.strategies.impl;

import com.example.uber.dto.RideRequestDto;
import com.example.uber.entities.Driver;
import com.example.uber.strategies.DriverMatchingStrategy;

import java.util.List;

public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMatchingDriver(RideRequestDto rideRequestDto) {
        return List.of();
    }
}
