package com.ynov.grosieuxClement.controllers;

import com.ynov.grosieuxClement.models.Admin;
import com.ynov.grosieuxClement.models.Role;
import com.ynov.grosieuxClement.models.User;
import com.ynov.grosieuxClement.repositories.RoleRepo;
import com.ynov.grosieuxClement.services.AuthService;
import com.ynov.grosieuxClement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    RoleRepo roleRepo;

    private ResponseEntity<?> userExisteResponse (User entity){
        Optional<User> user = userService.getUserByEmail(entity.getEmail());
        if(user.isPresent())
            return new ResponseEntity<>(
                    "l'email existe déjà",
                    HttpStatus.CONFLICT
            );
        return null;
    }

    @PostMapping("signup")
    public ResponseEntity<?> adminRegister(@RequestBody Admin entity){
        ResponseEntity<?> res = userExisteResponse(entity);
        if (res != null)
            return res;
        Optional<Role> role = roleRepo.findByRoleName(Role.RoleEnum.ADMIN.name());
        if(role.isEmpty()){
            return new ResponseEntity<>(
                    "Une erreur est servenue !",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(
                authService.registerAdmin(entity, role.get()),
                HttpStatus.CREATED
        );
    }
}
