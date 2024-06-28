package com.example.controllers;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.repositories.application.models.userModels.LoginModel;
import com.example.repositories.application.models.userModels.RegisterModel;
import com.example.repositories.application.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public Object SignUp(@RequestBody Map<String, String> req) {
        var res = new HashMap<String, Object>();
        try {
            var registerModel = new RegisterModel();
            registerModel.email = req.get("email").toString();
            registerModel.displayName = req.get("displayName").toString();
            registerModel.firstName = req.get("firstName").toString();
            registerModel.lastName = req.get("lastName").toString();
            registerModel.password = req.get("password").toString();
            registerModel.dob = req.get("dob").toString();

            userService.Register(registerModel);
            
            res.put("state", String.valueOf(1));
            res.put("mess", "Sign-up successful!");
        } catch (Exception e) {
            res.put("state", String.valueOf(-1));
            res.put("mess", "Registration failed: " + e.getMessage());
        }
        return res;
    }

    @PostMapping("/sign-in")
    public Object postMethodName(@RequestBody Map<String, String> req) {
        var res = new HashMap<String, Object>();
        try {
            var loginModel = new LoginModel();
            loginModel.email = req.get("email");
            loginModel.password = req.get("password");
            var result = userService.Login(loginModel);
            if (result != null) {
                if (result.get("token") != null) {
                    res.put("state", String.valueOf(1));
                    res.put("mess", "Sign-up successful!");
                    res.put("data", result);
                } else {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "Password invalid!");
                }
            } else {
                res.put("state", String.valueOf(0));
                res.put("mess", "Email is not exist");
            }
        } catch (Exception e) {
            res.put("state", String.valueOf(-1));
            res.put("mess", "Registration failed: " + e.getMessage());
        }
        return res;
    }

}
