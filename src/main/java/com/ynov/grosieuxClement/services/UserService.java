package com.ynov.grosieuxClement.services;

import com.ynov.grosieuxClement.models.Role;
import com.ynov.grosieuxClement.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    void addRoleToUser(User user, Role role);
}
