package com.example.controllers;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.repositories.application.common.SecurityMethod;
import com.example.repositories.application.models.userModels.LoginModel;
import com.example.repositories.application.models.userModels.RegisterModel;
import com.example.repositories.application.services.EmailService;
import com.example.repositories.application.services.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/authenticate")
public class AuthController {
    // #region DI
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
    // #endregion

    // #region action
    // req body
    /*
     * email
     * displayName
     * firstName
     * lastName
     * password
     * dob
     */
    @PostMapping("/sign-up")
    public CompletableFuture<Object> SignUp(@RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                var registerModel = new RegisterModel();
                registerModel.email = req.get("email").toString();
                registerModel.displayName = req.get("displayName").toString();
                registerModel.firstName = req.get("firstName").toString();
                registerModel.lastName = req.get("lastName").toString();
                registerModel.password = req.get("password").toString();
                registerModel.dob = req.get("dob").toString();
                // send email
                emailService.sendEmail(
                        "learntoteach2023@gmail.com", // change 'toEmail' to email in req
                        "Verify Email",
                        String.format("<a href='%s://%s:%d'/>Click here</a>"));
                // add new user
                userService.Register(registerModel);
                // prepare data res
                res.put("state", String.valueOf(1));
                res.put("mess", "Check Your mail to verify Email!");
            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }

    // req body
    /*
     * email
     * password
     */
    @PostMapping("/sign-in")
    public CompletableFuture<Object> SignIn(@RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                var loginModel = new LoginModel();
                loginModel.email = req.get("email");
                loginModel.password = req.get("password");
                // login
                var result = userService.Login(loginModel);
                // checking
                if (result != null) {
                    if (result.get("token") != null) {
                        // successful
                        res.put("state", String.valueOf(1));
                        res.put("mess", "Sign-up successful!");
                        res.put("data", result);
                    } else {
                        // password invalid
                        res.put("state", String.valueOf(0));
                        res.put("mess", "Password invalid!");
                    }
                } else {
                    // email is not exist
                    res.put("state", String.valueOf(0));
                    res.put("mess", "Email is not exist");
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
     * email
     */
    @PostMapping("/forgot-password")
    public CompletableFuture<Object> ForgotPassword(HttpSession session, @RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                var email = req.get("email");
                var checkingResult = userService.IsExistByEmail(email);
                if (checkingResult) {
                    // generate OTP
                    String otp = SecurityMethod.generateOTP(10);
                    // send OTP to email
                    emailService.sendEmail(
                            "learntoteach2023@gmail.com", // change 'toEmail' to email in req
                            "Forgot Password",
                            String.format(
                                    "There is your OTP code <div style=\"color: blue;\">%s</div>" +
                                            "<div style=\"color: red; frontWeight: bolder;\">Don't share it with anyone</div>",
                                    otp));
                    // prepare token to reset password
                    var user = userService.GetUserByEmail(email);
                    var tokenResetPassword = SecurityMethod.generateTokenAccess(user.getId(), user.getTokenAccess());
                    // add OTP and tokenResetPassword to session
                    var timeOut = 210;
                    session.setMaxInactiveInterval(timeOut);
                    session.setAttribute(otp, tokenResetPassword);
                    session.setAttribute(tokenResetPassword, user.getTokenAccess());
                    // prepare data res
                    var data = new HashMap<String, Object>();
                    data.put("time", String.valueOf(timeOut / 2));
                    res.put("state", String.valueOf(1));
                    res.put("data", data);
                    res.put("mess", "OTP have been sent, please check your email");
                } else {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "User is not exist!");
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
     * otp
     */
    @PostMapping("/verify-otp")
    public CompletableFuture<Object> VerifyOTP(HttpSession session, @RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                var otp = req.get("otp");
                // get data from session with OTP
                var dataSession = session.getAttribute(otp);
                // checking
                if (dataSession != null) {
                    String tokenResetPassword = (String) dataSession;
                    // prepare data res
                    var data = new HashMap<String, Object>();
                    data.put("tokenResetPassword", tokenResetPassword);
                    res.put("state", String.valueOf(1));
                    res.put("data", data);
                    res.put("mess", "");
                } else {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "wrong OTP");
                }
            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }
    //req body
    /*
     * newPassword
     */
    //req param
    /*
     * tokenResetPassword
     */
    @PostMapping("/reset-password")
    public CompletableFuture<Object> ChangePassword(
            HttpSession session,
            @RequestBody Map<String, String> req,
            @RequestParam String tokenResetPassword) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                var newPassword = req.get("newPassword");
                var dataSession = session.getAttribute(tokenResetPassword);
                // checking token in session
                if (dataSession != null) {
                    String tokenAccess = (String) dataSession;
                    // reset password
                    var result = userService.ResetPassword(tokenAccess, newPassword);
                    //prepare data res
                    if (result) {
                        res.put("state", String.valueOf(1));
                        res.put("mess", "password reset successful!");
                    }else{
                        res.put("state", String.valueOf(0));
                        res.put("mess", "password reset fail!");
                    }
                } else {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "password reset expires!");
                }
            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }
}
