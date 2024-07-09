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

import com.example.business.application.common.SecurityMethod;
import com.example.business.application.models.userModels.LoginModel;
import com.example.business.application.models.userModels.RegisterModel;
import com.example.business.application.services.EmailService;
import com.example.business.application.services.AuthnService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/authenticate")
public class AuthController {
    // #region DI
    @Autowired
    private AuthnService authnService;

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
     * clientURL
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
                var rootUrlClient = req.get("clientURL");
                // checking email is exist
                var checking = authnService.IsExistByEmail(registerModel.email);
                if (checking) {
                    res.put("mess", "Email already exists");
                    res.put("state", 0);
                    return res;
                }
                // add new user
                var user = authnService.Register(registerModel);
                if (user != null) {
                    // send email
                    var tokenVerifyEmail = user.getTokenVerifyEmail();
                    var verifyEmailURL = String.format("%s%s", rootUrlClient, tokenVerifyEmail);
                    emailService.sendEmail(
                            "learntoteach2023@gmail.com", // change 'toEmail' to email in req
                            "Verify Email",
                            "<a href='" + verifyEmailURL + "'>Click here</a> to verify your email. "
                                    + "It will be expired in " + authnService.expired + " minutes");
                    // prepare data res
                    res.put("state", String.valueOf(1));
                    res.put("mess", "Check Your mail to verify Email!");
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
     * clientURL
     */
    @PostMapping("/token-verify-email")
    public CompletableFuture<Object> GetTokenVerifyEmail(@RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                var email = req.get("email");
                var rootUrlClient = req.get("clientURL");
                var result = authnService.GenerateTokenVerifyEmail(email);
                var verifyEmailURL = String.format("%s%s", rootUrlClient, result);
                emailService.sendEmail(
                        "learntoteach2023@gmail.com", // change 'toEmail' to email in req
                        "Verify Email",
                        "<a href='" + verifyEmailURL + "'>Click here</a> to verify your email. "
                                + "It will be expired in " + authnService.expired + " minutes");
            } catch (Exception e) {
                res.put("state", String.valueOf(-1));
                res.put("mess", "Registration failed: " + e.getMessage());
            }
            return res;
        });
    }

    // req param
    /*
     * token
     */
    @PostMapping("/verify-email")
    public CompletableFuture<Object> VerifyEmail(@RequestParam String tokenVerifyEmail) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                var result = authnService.VerifyEmail(tokenVerifyEmail);
                if (result) {
                    // prepare data res
                    res.put("state", String.valueOf(1));
                    res.put("mess", "verify email successful!");
                } else {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "verify email expired");
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
     * password
     * tokenCode
     */
    @PostMapping("/sign-in")
    public CompletableFuture<Object> SignIn(HttpServletRequest request, @RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            String scheme = request.getScheme();
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            // Construct the base URL
            String baseUrl = scheme + "://" + serverName + ":" + serverPort;
            var res = new HashMap<String, Object>();
            try {
                var loginModel = new LoginModel();
                loginModel.email = req.get("email");
                loginModel.password = req.get("password");
                var tokenCode = req.get("tokenCode");
                if (tokenCode == null || tokenCode.isEmpty()) {
                    res.put("state", String.valueOf(0));
                    res.put("mess", "Must be have 'tokenCode' to get token");
                }
                // login
                var result = authnService.Login(loginModel, tokenCode);
                // checking
                if (result != null) {
                    if (result.get("token") != null) {
                        // successful
                        result.put("avatar", baseUrl + "/public/" + result.get("avatar"));
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

    // req
    /*
     * tokenCode
     */
    @PostMapping("/sign-out")
    public CompletableFuture<Object> SignOut(@RequestBody Map<String, String> req) {
        return CompletableFuture.supplyAsync(() -> {
            var res = new HashMap<String, Object>();
            try {
                var tokenCode = req.get("tokenCode");
                var result = authnService.Logout(tokenCode);
                if (result) {
                    res.put("state", String.valueOf(1));
                    // res.put("mess", "");
                } else {
                    res.put("state", String.valueOf(0));
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
                var checkingResult = authnService.IsExistByEmail(email);
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
                    var user = authnService.GetUserByEmail(email);
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

    // req body
    /*
     * newPassword
     */
    // req param
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
                    var result = authnService.ResetPassword(tokenAccess, newPassword);
                    // prepare data res
                    if (result) {
                        res.put("state", String.valueOf(1));
                        res.put("mess", "password reset successful!");
                    } else {
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
