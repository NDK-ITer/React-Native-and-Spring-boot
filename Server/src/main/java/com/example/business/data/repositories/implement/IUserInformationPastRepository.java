package com.example.business.data.repositories.implement;

import org.springframework.stereotype.Repository;

import com.example.business.data.entities.UserInformationPast;
import com.example.business.data.repositories.IBaseRepository;

@Repository
public interface IUserInformationPastRepository extends IBaseRepository<UserInformationPast, String> {
    
}
