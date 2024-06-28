package com.example.repositories.data.repositories.implement;

import org.springframework.stereotype.Repository;

import com.example.repositories.data.entities.Role;
import com.example.repositories.data.repositories.IBaseRepository;

@Repository
public interface IRoleRepository extends IBaseRepository<Role, String> {
    Role findByNormalizeName(String normalizeName);
}
