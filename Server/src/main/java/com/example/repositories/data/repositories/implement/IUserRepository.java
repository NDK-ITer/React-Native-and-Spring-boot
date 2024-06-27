package com.example.repositories.data.repositories.implement;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.repositories.data.entities.User;
import com.example.repositories.data.repositories.IBaseRepository;

@Repository
public interface IUserRepository extends IBaseRepository<User, String> {
    User findByEmail(String email);
    User findByRouteCode(String userName);
}