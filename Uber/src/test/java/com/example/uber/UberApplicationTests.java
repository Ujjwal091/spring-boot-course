package com.example.uber;

import com.example.uber.repositories.RideRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class UberApplicationTests {
    @Autowired
    private RideRepository rideRepository;

    @Test
    void contextLoads() {
        var res = rideRepository.findByDriverId(1L, PageRequest.of(0, 10));
        res.getContent();
    }


}
