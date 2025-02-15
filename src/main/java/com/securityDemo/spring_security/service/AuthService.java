package com.securityDemo.spring_security.service;

import com.securityDemo.spring_security.payload.LoginDto;
import com.securityDemo.spring_security.repository.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
