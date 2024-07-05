package com.example.business.application.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.business.application.common.FileMethod;
import com.example.business.application.common.SecurityMethod;
import com.example.business.application.libs.JWTMethod;
import com.example.business.application.models.userModels.LoginModel;
import com.example.business.application.models.userModels.RegisterModel;
import com.example.business.data.entities.User;
import com.example.business.data.repositories.implement.IRoleRepository;
import com.example.business.data.repositories.implement.IUserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {

    // #region Repositories
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    // #endregion
    @Autowired
    private JWTMethod jwtMethod;
    
    public final int expired = 5;
    // #region Checking
    public boolean IsExistById(String id) {
        var user = userRepository.findById(id);
        if (user == null)
            return false;
        return true;
    }

    public boolean IsExistByEmail(String email) {
        var user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        return true;
    }
    // #endregion

    // #region Business
    public Page<User> GetAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User GetUserById(String id) {
        var user = userRepository.findById(id);
        return user.get();
    }

    public User GetUserByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return user;
    }

    public User Register(RegisterModel registerModel) {
        // get role with default id USER
        var role = roleRepository.findByNormalizeName("USER");
        // avatar default
        var avatar = FileMethod.copyAndRenameImage(
                String.format("Avatar-%s.png", Base64.getEncoder().encodeToString(registerModel.email.getBytes())),
                "default-avatar.png");
        // create new user
        var newUser = new User();
        newUser.setEmail(registerModel.email);
        newUser.setDisplayName(registerModel.displayName);
        newUser.setFirstName(registerModel.firstName);
        newUser.setLastName(registerModel.lastName);
        newUser.setDOB(LocalDateTime.parse(registerModel.dob, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        newUser.setPasswordHash(SecurityMethod.encodePassword(registerModel.password));
        newUser.setAvatar(avatar);
        newUser.setRole(role);
        newUser.setTokenAccess(SecurityMethod.generateTokenAccess());
        newUser.setTokenVerifyEmail(SecurityMethod.generateTokenAccess());
        newUser.setTokenVerifyEmailExpired(LocalDateTime.now().plusMinutes(expired));
        newUser.setRouteCode(SecurityMethod.generateOTP(10));
        // save new user
        var result = userRepository.save(newUser);
        return result;
    }

    public HashMap<String, String> Login(LoginModel loginModel) {
        var result = new HashMap<String, String>();
        var user = userRepository.findByEmail(loginModel.email);
        if (user == null)
            return null;
        String jwt = "";
        result.put("displayName", user.getDisplayName());
        result.put("avatar", user.getAvatar());
        result.put("routeCode", user.getRouteCode());
        if (SecurityMethod.checkPassword(loginModel.password, user.getPasswordHash())) {
            // generate JWT
            var payload = new HashMap<String, String>();
            payload.put("tokenAccess", user.getTokenAccess());
            payload.put("role", user.getRole().getNormalizeName());
            jwt = jwtMethod.generateJwtToken(payload);
            result.put("token", jwt);
        }
        return result;
    }

    public boolean ResetPassword(String tokenAccess, String newPassword) {
        if ((tokenAccess.isEmpty() || tokenAccess == null) && (newPassword.isEmpty() || newPassword == null))
            return false;
        var user = userRepository.findByTokenAccess(tokenAccess);
        if (user == null)
            return false;
        user.setPasswordHash(SecurityMethod.encodePassword(newPassword));
        userRepository.save(user);
        return true;
    }

    public boolean VerifyEmail(String tokenVerifyEmail) {
        if (tokenVerifyEmail.isEmpty() || tokenVerifyEmail == null)
            return false;
        var user = userRepository.findByTokenVerifyEmail(tokenVerifyEmail);
        if (user == null)
            return false;
        if (!user.getTokenVerifyEmailExpired().isAfter(LocalDateTime.now()))
            return false;
        user.setIsVerified(true);
        user.setVerifiedDate(LocalDateTime.now());
        user.setTokenVerifyEmail(null);
        user.setTokenVerifyEmailExpired(null);
        userRepository.save(user);
        return true;
    }

    public String GenerateTokenVerifyEmail(String email) {
        if (email.isEmpty() || email == null)
            return null;
        var user = userRepository.findByEmail(email);
        if (user == null)
            return null;
        var tokenVerifyEmail = SecurityMethod.generateTokenAccess();
        user.setTokenVerifyEmail(tokenVerifyEmail);
        user.setTokenVerifyEmailExpired(LocalDateTime.now().plusMinutes(expired));
        userRepository.save(user);
        return tokenVerifyEmail;
    }
    // #endregion
}
