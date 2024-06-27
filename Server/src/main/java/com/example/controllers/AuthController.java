package com.example.controllers;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.repositories.application.models.userModels.RegisterModel;
import com.example.repositories.application.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public HashMap<String, String> SignUp(@RequestBody Map<String, String> data) {
        var res = new HashMap<String, String>();
        try {
            var registerModel = new RegisterModel();
            registerModel.email = data.get("email").toString();
            registerModel.displayName = data.get("displayName").toString();
            registerModel.firstName = data.get("firstName").toString();
            registerModel.lastName = data.get("lastName").toString();
            registerModel.password = data.get("password").toString();
            registerModel.dob = data.get("dob").toString();
            userService.Register(registerModel);
            res.put("state", String.valueOf(1));
            res.put("message","Sign-up successful!");
        } catch (Exception e) {
            res.put("state", String.valueOf(-1));
            res.put("message", "Registration failed: " + e.getMessage());
        }
        return res;
    }
}
