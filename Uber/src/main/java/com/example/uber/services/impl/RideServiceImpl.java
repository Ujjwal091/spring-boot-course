package com.example.uber.services.impl;

import com.example.uber.entities.Driver;
import com.example.uber.entities.Ride;
import com.example.uber.entities.RideRequest;
import com.example.uber.entities.Rider;
import com.example.uber.entities.enums.RideRequestStatus;
import com.example.uber.entities.enums.RideStatus;
import com.example.uber.exceptions.ResourceNotFoundException;
import com.example.uber.repositories.RideRepository;
import com.example.uber.services.RideRequestService;
import com.example.uber.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;


    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId).orElseThrow(
                () -> new ResourceNotFoundException("Ride not found with id: " + rideId)
        );
    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRM);
        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return rideRepository.findByRiderId(rider.getId(), pageRequest);
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return rideRepository.findByDriverId(driver.getId(), pageRequest);
    }

    private String generateOTP() {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int otp = random.nextInt(10_000);
        return String.format("%04d", otp);
    }
}
