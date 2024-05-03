package com.ynov.grosieuxClement.services;

import com.ynov.grosieuxClement.models.Admin;
import com.ynov.grosieuxClement.models.Role;
import com.ynov.grosieuxClement.models.User;

public interface AuthService {
//    User register(User entity, Role role);
    Admin registerAdmin(Admin entity, Role role);
    String loginAdmin(Admin entity, String password);

    String loginUser(User user, String password);
    User registerUser(User entity, Role role);
}
