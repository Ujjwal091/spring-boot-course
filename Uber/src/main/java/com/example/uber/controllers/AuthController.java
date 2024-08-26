package com.example.uber.controllers;


import com.example.uber.dto.SignupDto;
import com.example.uber.dto.UserDto;
import com.example.uber.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
