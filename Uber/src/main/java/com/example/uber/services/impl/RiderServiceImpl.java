package com.example.uber.services.impl;

import com.example.uber.dto.DriverDto;
import com.example.uber.dto.RideDto;
import com.example.uber.dto.RideRequestDto;
import com.example.uber.dto.RiderDto;
import com.example.uber.entities.Ride;
import com.example.uber.entities.RideRequest;
import com.example.uber.entities.Rider;
import com.example.uber.entities.User;
import com.example.uber.entities.enums.RideRequestStatus;
import com.example.uber.entities.enums.RideStatus;
import com.example.uber.exceptions.ResourceNotFoundException;
import com.example.uber.repositories.RideRequestRepository;
import com.example.uber.repositories.RiderRepository;
import com.example.uber.services.DriverService;
import com.example.uber.services.RatingService;
import com.example.uber.services.RideService;
import com.example.uber.services.RiderService;
import com.example.uber.strategies.RiderStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {
    private final ModelMapper modelMapper;
    private final RiderStrategyManager riderStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideService rideService;
    private final DriverService driverService;
    private final RatingService ratingService;


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
        Rider rider = getCurrentRider();
        Ride ride = rideService.getRideById(rideId);

        if (!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider is not own this ride with id: " + ride.getId());
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: " + ride.getRideStatus());
        }

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);


        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider is not the owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status: "+ride.getRideStatus());
        }

        return ratingService.rateDriver(ride, rating);
    }

    @Override
    public RiderDto getMyProfile() {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        Rider rider = getCurrentRider();
        return rideService.getAllRidesOfRider(rider, pageRequest).map(ride -> modelMapper.map(ride, RideDto.class));
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
