package com.example.uber.services.impl;

import com.example.uber.dto.DriverDto;
import com.example.uber.dto.SignupDto;
import com.example.uber.dto.UserDto;
import com.example.uber.entities.Driver;
import com.example.uber.entities.User;
import com.example.uber.entities.enums.Role;
import com.example.uber.exceptions.ResourceNotFoundException;
import com.example.uber.exceptions.RuntimeConflictException;
import com.example.uber.repositories.UserRepository;
import com.example.uber.security.JWTService;
import com.example.uber.services.AuthService;
import com.example.uber.services.DriverService;
import com.example.uber.services.RiderService;
import com.example.uber.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.example.uber.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String[] login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new String[]{accessToken, refreshToken};
    }

    @Transactional
    @Override
    public UserDto signup(SignupDto signupDto) {
        Optional<User> user = userRepository.findByEmail(signupDto.getEmail());
        if (user.isPresent()) {
            throw new RuntimeConflictException("Email already in use");
        }

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));

        User savedUser = userRepository.save(mappedUser);

        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public DriverDto onboardNewDriver(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (user.getRoles().contains(DRIVER))
            throw new RuntimeConflictException("User with id " + userId + " is already a Driver");

        Driver createDriver = Driver.builder().user(user).rating(0.0).isAvailable(true).build();

        user.getRoles().add(DRIVER);
        userRepository.save(user);

        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found " + "with id: " + userId));

        return jwtService.generateAccessToken(user);
    }
}
