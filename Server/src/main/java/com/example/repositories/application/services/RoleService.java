package com.example.repositories.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repositories.data.repositories.implement.IRoleRepository;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository roleRepository;
}
