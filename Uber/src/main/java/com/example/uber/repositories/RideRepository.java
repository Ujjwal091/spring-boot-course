package com.example.uber.repositories;

import com.example.uber.entities.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    Page<Ride> findByRiderId(Long riderId, PageRequest pageRequest);

    Page<Ride> findByDriverId(Long driverId, PageRequest pageRequest);
}
