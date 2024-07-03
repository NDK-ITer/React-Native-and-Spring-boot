package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.repositories.application.services.EmailService;

import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/hc")
public class HelloController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/hello")
    public Object SayHello(
    // @RequestAttribute("userIdentify") Claims userIdentify
    ) {
        // String userId = userIdentify.get("id", String.class);
        // String role = userIdentify.get("role", String.class);
        try {
            String scheme = ServletUriComponentsBuilder.fromCurrentRequest().build().getScheme();
            String host = ServletUriComponentsBuilder.fromCurrentRequest().build().getHost();
            int port = ServletUriComponentsBuilder.fromCurrentRequest().build().getPort();
            emailService.sendEmail(
                    "learntoteach2023@gmail.com",
                    "Verify Email",
                    String.format("<a href='%s://%s:%d'/>Click here</a>",scheme, host, port)
            );
            return "Hello World";
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }
}
