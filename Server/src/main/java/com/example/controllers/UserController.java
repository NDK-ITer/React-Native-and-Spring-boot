package com.example.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;

import com.example.business.application.services.UserService;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/user")
public class UserController {
    // #region DI
    @Autowired
    private UserService userService;
    // #endregion

    // #region action
    @GetMapping("/my-information/get")
    public CompletableFuture<Object> GetInformation(@RequestAttribute("userInformation") Claims userInformation) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                String tokenAccess = userInformation.get("tokenAccess", String.class);
                var userModel = userService.GetUser(tokenAccess);
                if (userModel != null) {
                    res.put("data", userModel);
                    res.put("state", String.valueOf(1));
                }else{
                    res.put("state", String.valueOf(0));
                    res.put("mess", "User not found!");
                }
            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }

    @PostMapping("/my-information/edit")
    public CompletableFuture<Object> EditInformation(@RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {

            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }

    @PostMapping("/my-information/upload-avatar")
    public CompletableFuture<Object> UpdateAvatar(@RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {

            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }

    // #endregion
}
