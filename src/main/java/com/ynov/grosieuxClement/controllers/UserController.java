package com.ynov.grosieuxClement.controllers;

import com.ynov.grosieuxClement.models.Admin;
import com.ynov.grosieuxClement.models.Role;
import com.ynov.grosieuxClement.models.User;
import com.ynov.grosieuxClement.repositories.RoleRepo;
import com.ynov.grosieuxClement.services.AdminService;
import com.ynov.grosieuxClement.services.AuthService;
import com.ynov.grosieuxClement.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    RoleRepo roleRepo;

    private ResponseEntity<?> userExisteResponse(User entity){
        Optional<User> user = userService.getUserByEmail(entity.getEmail());
        if(user.isPresent())
            return new ResponseEntity<>(
                    "l'email existe déjà",
                    HttpStatus.CONFLICT
            );
        return null;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody Map<String, String> request){
        String email = request.get("username");
        String password = request.get("password");
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty())
            return new ResponseEntity(
                    "User n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        String jwt = authService.loginUser(user.get(), password);
        if (jwt == null)
            return new ResponseEntity<>(
                    "Mot de passe incorrect",
                    HttpStatus.FORBIDDEN
            );
        return new ResponseEntity<>(
                jwt,
                HttpStatus.OK
        );
    }
}
