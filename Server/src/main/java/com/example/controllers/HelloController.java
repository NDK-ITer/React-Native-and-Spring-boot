package com.example.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.repositories.application.common.SecurityMethod;

@RestController
@RequestMapping("/api/hc")
public class HelloController {

    @GetMapping("/hello")
    public String SayHello() {
        String p = SecurityMethod.encodePassword("Hello World");
        boolean r = SecurityMethod.checkPassword("Hello World", p);
        String otp = SecurityMethod.generateOTP(6);
        return "Hello World";
    }
}
