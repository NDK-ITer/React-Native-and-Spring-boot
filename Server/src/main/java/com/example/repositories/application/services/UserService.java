package com.example.repositories.application.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.repositories.application.common.SecurityMethod;
import com.example.repositories.application.libs.JWTMethod;
import com.example.repositories.application.models.userModels.LoginModel;
import com.example.repositories.application.models.userModels.RegisterModel;
import com.example.repositories.data.entities.User;
import com.example.repositories.data.repositories.implement.IRoleRepository;
import com.example.repositories.data.repositories.implement.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private JWTMethod jwtMethod;

    public Page<User> GetAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User Register(RegisterModel registerModel) {
        var role = roleRepository.findByNormalizeName("USER");
        var newUser = new User();
        newUser.setEmail(registerModel.email);
        newUser.setDisplayName(registerModel.displayName);
        newUser.setFirstName(registerModel.firstName);
        newUser.setLastName(registerModel.lastName);
        newUser.setDOB(LocalDateTime.parse(registerModel.dob, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        newUser.setPasswordHash(SecurityMethod.encodePassword(registerModel.password));
        newUser.setRole(role);
        newUser.setTokenAccess(SecurityMethod.generateTokenAccess());
        newUser.setRouteCode(SecurityMethod.generateOTP(10));

        var result = userRepository.save(newUser);
        return result;
    }

    public HashMap<String, String> Login(LoginModel loginModel) {
        var result = new HashMap<String, String>();
        var user = userRepository.findByEmail(loginModel.email);
        if(user == null) return null;
        String jwt = "";
        result.put("displayName", user.getDisplayName());
        result.put("avatar", user.getAvatar());
        if (SecurityMethod.checkPassword(loginModel.password, user.getPasswordHash())) {
            var payload = new HashMap<String, String>();
            payload.put("id", user.getId());
            payload.put("role", user.getRole().getNormalizeName());
            jwt = jwtMethod.generateJwtToken(payload);
            result.put("token", jwt);
        }
        return result;
    }
}
