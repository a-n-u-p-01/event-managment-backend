package com.anupam.eventManagement.service.impl;

import com.anupam.eventManagement.entity.User;
import com.anupam.eventManagement.repository.UserRepository;
import com.anupam.eventManagement.response.LoginUserDto;
import com.anupam.eventManagement.response.RegisterUserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = User.builder().fullName(input.getFullName()).email(input.getEmail()).password(passwordEncoder.encode(input.getPassword())).authProvider("LOCAL").build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

}
