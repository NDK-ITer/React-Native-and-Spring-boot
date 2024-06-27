package com.example.repositories.data.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.repositories.data.entities.Role;
import com.example.repositories.data.repositories.implement.IRoleRepository;

@Component
public class DataSeeder implements CommandLineRunner{
    
    // @Autowired
    // private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
    }

    private void seedRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setNormalizeName("ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setNormalizeName("USER");
            roleRepository.save(userRole);
        }
    }
    
}
