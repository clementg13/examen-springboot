package com.ynov.grosieuxClement.servicesImplem;

import com.ynov.grosieuxClement.models.Role;
import com.ynov.grosieuxClement.models.User;
import com.ynov.grosieuxClement.repositories.UserRepo;
import com.ynov.grosieuxClement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserImplem implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public void addRoleToUser(User user, Role role){
        user.addRole(role);
        userRepo.save(user);
    }
}
