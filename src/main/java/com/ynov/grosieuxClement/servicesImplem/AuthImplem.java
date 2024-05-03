package com.ynov.grosieuxClement.servicesImplem;

import com.ynov.grosieuxClement.models.Admin;
import com.ynov.grosieuxClement.models.Role;
import com.ynov.grosieuxClement.models.User;
import com.ynov.grosieuxClement.security.JwtService;
import com.ynov.grosieuxClement.services.AdminService;
import com.ynov.grosieuxClement.services.AuthService;
import com.ynov.grosieuxClement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthImplem implements AuthService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AdminService adminService;

    @Autowired
    JwtService jwtService;

    @Override
    public Admin registerAdmin(Admin entity, Role role) {
        String passwordEncoded = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(passwordEncoded);
        adminService.addRoleToAdmin(entity, role);
        return adminService.createAdmin(entity);
    }

//    @Override
//    public User register(User entity, Role role) {
//        String passwordEncoded = bCryptPasswordEncoder.encode(entity.getPassword());
//        entity.setPassword(passwordEncoded);
//        userService.addRoleToUser(entity, role);
//        return userService.createUser(entity);
//    }
}
