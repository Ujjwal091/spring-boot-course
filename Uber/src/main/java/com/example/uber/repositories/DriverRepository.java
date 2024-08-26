package com.example.uber.repositories;

import com.example.uber.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query("SELECT d " +
            "from Driver d " +
            "WHERE d.isAvailable=true " +
            "AND FUNCTION('ST_DWithin', d.currentLocation, :pickupLocation, 10000) = true " +
            "ORDER BY Function('ST_Distance', d.currentLocation, :pickupLocation) " +
            "LIMIT 10")
    List<Driver> findTenNearestDrivers(Point pickupLocation);


    @Query("SELECT d " +
            "FROM Driver d " +
            "WHERE d.isAvailable = true " +
            "AND FUNCTION('ST_DWithin', d.currentLocation, :pickupLocation, 10000) = true " +
            "ORDER BY d.rating DESC " +
            "LIMIT 10")
    List<Driver> findTenNearestTopRatedDriver(Point pickupLocation);
}
