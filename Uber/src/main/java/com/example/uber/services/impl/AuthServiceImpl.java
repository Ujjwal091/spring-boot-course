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
import com.example.uber.services.AuthService;
import com.example.uber.services.DriverService;
import com.example.uber.services.RiderService;
import com.example.uber.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    @Override
    public String login(String email, String password) {
        return "";
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
        User savedUser = userRepository.save(mappedUser);

        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public DriverDto onboardNewDriver(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (user.getRoles().contains(DRIVER))
            throw new RuntimeConflictException("User with id " + userId + " is already a Driver");

        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .isAvailable(true)
                .build();

        user.getRoles().add(DRIVER);
        userRepository.save(user);

        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }
}
