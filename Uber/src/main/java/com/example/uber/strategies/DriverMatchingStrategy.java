package com.example.uber.strategies;

import com.example.uber.dto.RideRequestDto;
import com.example.uber.entities.Driver;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequestDto rideRequestDto);
}
