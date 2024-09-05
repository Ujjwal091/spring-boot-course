package com.example.uber.controllers;


import com.example.uber.dto.DriverDto;
import com.example.uber.dto.SignupDto;
import com.example.uber.dto.UserDto;
import com.example.uber.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    UserDto signup(@RequestBody @Valid SignupDto signupDto) {
        return authService.signup(signupDto);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId) {
        return new ResponseEntity<>(authService.onboardNewDriver(userId), HttpStatus.CREATED);
    }
}
