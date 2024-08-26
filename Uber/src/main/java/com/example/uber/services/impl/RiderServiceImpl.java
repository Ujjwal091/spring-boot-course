package com.example.uber.services.impl;

import com.example.uber.dto.DriverDto;
import com.example.uber.dto.RideDto;
import com.example.uber.dto.RideRequestDto;
import com.example.uber.dto.RiderDto;
import com.example.uber.entities.RideRequest;
import com.example.uber.entities.Rider;
import com.example.uber.entities.User;
import com.example.uber.entities.enums.RideRequestStatus;
import com.example.uber.exceptions.ResourceNotFoundException;
import com.example.uber.repositories.RideRequestRepository;
import com.example.uber.repositories.RiderRepository;
import com.example.uber.services.RiderService;
import com.example.uber.strategies.RiderStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final ModelMapper modelMapper;
    private final RiderStrategyManager riderStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;


    @Transactional
    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        Double fare = riderStrategyManager.rideFareStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);
        rideRequest.setRider(getCurrentRider());

        System.out.println(rideRequestDto);
        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        riderStrategyManager.driverMatchingStrategy(getCurrentRider().getRating()).findMatchingDriver(rideRequest);

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }

    @Override
    public RiderDto createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        rider = riderRepository.save(rider);
        return modelMapper.map(rider, RiderDto.class);
    }

    @Override
    public Rider getCurrentRider() {
        return riderRepository.findById(1L).orElseThrow(
                () -> new ResourceNotFoundException("Rider not found")
        );
    }
}
