package com.example.business.data.repositories.implement;

import org.springframework.stereotype.Repository;

import com.example.business.data.entities.Role;
import com.example.business.data.repositories.IBaseRepository;

@Repository
public interface IRoleRepository extends IBaseRepository<Role, String> {
    Role findByNormalizeName(String normalizeName);
}
