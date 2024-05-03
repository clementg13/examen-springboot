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

import java.util.Random;

@Service
public class AuthImplem implements AuthService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @Override
    public Admin registerAdmin(Admin entity, Role role) {
        String passwordEncoded = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(passwordEncoded);
        adminService.addRoleToAdmin(entity, role);
        return adminService.createAdmin(entity);
    }

    @Override
    public String loginAdmin(Admin entity, String password) {
        if(bCryptPasswordEncoder.matches(password, entity.getPassword()))
            return jwtService.generateToken(entity);
        return null;
    }

    @Override
    public String loginUser(User user, String password) {
        if(bCryptPasswordEncoder.matches(password, user.getPassword()))
            return jwtService.generateToken(user);
        return null;
    }

    @Override
    public User registerUser(User entity, Role role) {
        String passwordEncoded = bCryptPasswordEncoder.encode(entity.getPassword());
        entity.setPassword(passwordEncoded);
        // setCode a random code by random
        Random rand = new Random();
        int chiffreAleatoire = 0;

        for (int i = 0; i < 8; i++) {
            // Générer un chiffre aléatoire entre 0 et 9
            int chiffre = rand.nextInt(10);
            // Multiplier le chiffre généré par la puissance de 10 correspondante
            chiffreAleatoire = chiffreAleatoire * 10 + chiffre;
        }
        entity.setCode(chiffreAleatoire);
        userService.addRoleToUser(entity, role);
        return userService.createUser(entity);
    }
}
