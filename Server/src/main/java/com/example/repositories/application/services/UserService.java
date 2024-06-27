package com.example.repositories.application.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.repositories.application.common.SecurityMethod;
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

    public Page<User> GetAllUser(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public User Register(RegisterModel registerModel){
        var user = userRepository.findByEmail(registerModel.email);
        if(user != null) return null;

        var role = roleRepository.findByNormalizeName("USER");
        var newUser = new User();
        newUser.setEmail(registerModel.email);
        newUser.setDisplayName(registerModel.displayName);
        newUser.setFirstName(registerModel.firstName);
        newUser.setLastName(registerModel.lastName);
        newUser.setDOB(LocalDateTime.parse(registerModel.dob,DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        newUser.setPasswordHash(SecurityMethod.encodePassword(registerModel.password));
        newUser.setRole(role);
        newUser.setTokenAccess(SecurityMethod.generateTokenAccess());
        newUser.setRouteCode(SecurityMethod.generateOTP(10));

        var result = userRepository.save(newUser);
        return result;
    }
}
