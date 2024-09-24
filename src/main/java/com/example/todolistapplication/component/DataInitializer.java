package com.example.todolistapplication.component;

import com.example.todolistapplication.entity.Role;
import com.example.todolistapplication.entity.User;
import com.example.todolistapplication.repository.RoleRepository;
import com.example.todolistapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = createRoleIfNotFound();
        createUserIfNotFound(adminRole);
    }

    @Transactional
    Role createRoleIfNotFound() {
        Optional<Role> roleOpt = roleRepository.findByName("ROLE_ADMIN");

        if (roleOpt.isPresent()) {
            return roleOpt.get();
        }

        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Transactional
    void createUserIfNotFound(Role adminRole) {
        Optional<User> userOpt = userRepository.findByUsername("admin");
        if (userOpt.isPresent()) {
            return;
        }

        User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setPassword(passwordEncoder.encode("admin123!"));
        user.setRoles(Collections.singletonList(adminRole));
        userRepository.save(user);
    }
}
