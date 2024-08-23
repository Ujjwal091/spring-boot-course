package com.example.uber.dto;

import com.example.uber.entities.enums.PaymentMethod;
import com.example.uber.entities.enums.RideRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {
    private Long id;
    private Point pickupLocation;
    private Point destinationLocation;
    private LocalDateTime requestedTime;
    private RiderDto rider;
    private PaymentMethod paymentMethod;
    private RideRequestStatus rideRequestStatus;
}
