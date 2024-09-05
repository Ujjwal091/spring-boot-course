package com.example.uber.services;

import com.example.uber.dto.DriverDto;
import com.example.uber.dto.SignupDto;
import com.example.uber.dto.UserDto;

public interface AuthService {
    String login(String email, String password);

    UserDto signup(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId);
}
