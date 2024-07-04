package com.example.business.data.repositories.implement;

import org.springframework.stereotype.Repository;

import com.example.business.data.entities.User;
import com.example.business.data.repositories.IBaseRepository;

@Repository
public interface IUserRepository extends IBaseRepository<User, String> {
    User findByEmail(String email);
    User findByRouteCode(String userName);
    User findByTokenAccess(String tokenAccess);
}