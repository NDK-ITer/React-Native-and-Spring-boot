package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.business.application.services.EmailService;

import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/hc")
public class HelloController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/hello")
    public Object SayHello(
    @RequestAttribute("userInformation") Claims userIdentify
    ) {
        String userId = userIdentify.get("tokenAccess", String.class);
        String role = userIdentify.get("role", String.class);
        return "Hello World";
    }
}
