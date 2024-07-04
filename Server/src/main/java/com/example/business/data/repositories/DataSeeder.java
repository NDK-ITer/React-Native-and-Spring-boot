package com.example.business.data.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.business.data.entities.Role;
import com.example.business.data.repositories.implement.IRoleRepository;

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
            adminRole.setName("Admin");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setNormalizeName("USER");
            adminRole.setName("User");
            roleRepository.save(userRole);
        }
    }
    
}
