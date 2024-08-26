package com.example.uber.services.impl;

import com.example.uber.services.DistanceService;
import lombok.Data;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class DistanceServiceOSRMImp implements DistanceService {
    private static final String OSRM_API = "http://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point source, Point destination) {
        try {
            OSRMResponseDto osrmResponseDto = RestClient
                    .builder()
                    .baseUrl(OSRM_API)
                    .build()
                    .get()
                    .uri("%s,%s;%s,%s".formatted(source.getX(), source.getY(), destination.getX(), destination.getY()))
                    .retrieve()
                    .body(OSRMResponseDto.class);

            return osrmResponseDto.getRoutes().getFirst().getDistance() / 10_000;
        } catch (Exception e) {
            throw new RuntimeException("Error getting distance from OSRM " + e.getMessage());
        }
    }

    @Data
    static class OSRMResponseDto {
        private List<OSRMRoute> routes;

    }

    @Data
    static class OSRMRoute {
        private double distance;
    }

}
