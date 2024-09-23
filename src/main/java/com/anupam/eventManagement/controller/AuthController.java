package com.anupam.eventManagement.controller;

import com.anupam.eventManagement.entity.User;
import com.anupam.eventManagement.response.LoginResponse;
import com.anupam.eventManagement.response.LoginUserDto;
import com.anupam.eventManagement.response.RegisterUserDto;
import com.anupam.eventManagement.service.impl.AuthenticationService;
import com.anupam.eventManagement.service.impl.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        String jwtToken = jwtService.generateToken(registeredUser);
        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();
        loginResponse.setUserName(registeredUser.getFullName());
        loginResponse.setUserId(registeredUser.getId());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto , HttpServletResponse response) {
       User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();
        loginResponse.setUserName(authenticatedUser.getFullName());
        loginResponse.setUserId(authenticatedUser.getId());
        return ResponseEntity.ok(loginResponse);
    }

}
