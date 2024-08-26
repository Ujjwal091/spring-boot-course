package com.example.uber.services;

import com.example.uber.dto.DriverDto;
import com.example.uber.dto.RideDto;
import com.example.uber.dto.RideRequestDto;
import com.example.uber.dto.RiderDto;
import com.example.uber.entities.Rider;
import com.example.uber.entities.User;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    List<RideDto> getAllMyRides();

    RiderDto createNewRider(User user);

    Rider getCurrentRider();
}
