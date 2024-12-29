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
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    ResponseEntity<UserDto> signup(@RequestBody @Valid SignupDto signupDto) {
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId) {
        return new ResponseEntity<>(authService.onboardNewDriver(userId), HttpStatus.CREATED);
    }
}
