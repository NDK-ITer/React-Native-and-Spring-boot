package com.example.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.business.application.models.userModels.EditUserModel;
import com.example.business.application.services.UserService;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
                } else {
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

    // req body
    /*
     * displayName
     * firstName
     * lastName
     * dateOfBirth
     */
    @PutMapping("/my-information/edit")
    public CompletableFuture<Object> EditInformation(
            @RequestAttribute("userInformation") Claims userInformation,
            @RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                String tokenAccess = userInformation.get("tokenAccess", String.class);
                // Init EditUserModel
                var editUserModel = new EditUserModel();
                editUserModel.newDisplayName = req.get("displayName");
                editUserModel.newFirstName = req.get("firstName");
                editUserModel.newLastName = req.get("lastName");
                editUserModel.newDateOfBirth = req.get("dateOfBirth");
                editUserModel.tokenAccess = tokenAccess;
                // Edit user
                var result = userService.EditUser(editUserModel);
                if (result != null) {
                    res.put("state", String.valueOf(1));
                    res.put("mess", "Update successful !");
                    res.put("data", result);
                } else {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "Update fail !");
                }
            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }

    @PostMapping("/avatar/upload")
    public CompletableFuture<Object> UpdateAvatar(
            @RequestAttribute("userInformation") Claims userInformation,
            @RequestParam("avatar") MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                String tokenAccess = userInformation.get("tokenAccess", String.class);
                if (file != null) {
                    var result = userService.UploadAvatar(file, tokenAccess);
                    if (result) {
                        res.put("state", String.valueOf(1));
                        res.put("mess", "Update successful !");
                    }else {
                        res.put("state", String.valueOf(0));
                        res.put("mess", "Update fail !");
                    }
                }else {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "Update fail !");
                }
            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }

    // #endregion
}
