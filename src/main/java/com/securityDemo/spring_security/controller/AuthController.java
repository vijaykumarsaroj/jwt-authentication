package com.securityDemo.spring_security.controller;

import com.securityDemo.spring_security.payload.JWTAuthResponse;
import com.securityDemo.spring_security.payload.LoginDto;
import com.securityDemo.spring_security.payload.RegisterDto;
import com.securityDemo.spring_security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //build login

    @PostMapping(value = {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse>login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    //register api

    @PostMapping(value = {"/signup","/register"})
    public ResponseEntity<String>register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
