package com.example.uber.services;


import com.example.uber.dto.DriverDto;
import com.example.uber.dto.RiderDto;
import com.example.uber.entities.Ride;

public interface RatingService {

    DriverDto rateDriver(Ride ride, Integer rating);

    RiderDto rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}
