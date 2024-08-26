package com.example.uber.strategies;

import com.example.uber.entities.Driver;
import com.example.uber.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequest rideRequest);
}
