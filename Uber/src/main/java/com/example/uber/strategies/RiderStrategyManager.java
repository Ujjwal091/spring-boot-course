package com.example.uber.strategies;

import com.example.uber.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.example.uber.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.example.uber.strategies.impl.RideFareDefaultFareCalculationStrategy;
import com.example.uber.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RiderStrategyManager {
    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingStrategy;
    private final RideFareDefaultFareCalculationStrategy rideFareDefaultFareCalculationStrategy;


    public DriverMatchingStrategy driverMatchingStrategy(double riderRating) {
        if (riderRating > 4.0) {
            return driverMatchingHighestRatedStrategy;
        }
        return driverMatchingNearestStrategy;
    }

    public RideFareCalculationStrategy rideFareStrategy() {
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime) {
            return rideFareSurgePricingStrategy;
        } else {
            return rideFareDefaultFareCalculationStrategy;
        }
    }
}
