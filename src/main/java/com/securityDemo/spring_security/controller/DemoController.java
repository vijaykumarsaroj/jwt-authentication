package com.securityDemo.spring_security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String hello(){
        return "Hello World!";
    }
}
