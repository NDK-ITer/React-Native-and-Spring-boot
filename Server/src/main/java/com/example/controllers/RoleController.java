package com.example.controllers;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.business.application.services.RoleService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/authorize")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/get-all-role")
    public CompletableFuture<Object> GetAllRole() {
        var res = new HashMap<String, Object>();
        return CompletableFuture.supplyAsync(() -> {
            try {
                var listRole = roleService.GetAllRole();
                if (listRole.size() > 1) {
                    res.put("state", String.valueOf(1));
                    res.put("data", listRole);
                    res.put("mess", "");
                } else {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "the 'ROLE' of system is empty");
                }
            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }
}
