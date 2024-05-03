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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    RoleRepo roleRepo;

    private ResponseEntity<?> adminExisteResponse(Admin entity){
        Optional<Admin> admin = adminService.getAdminByEmail(entity.getEmail());
        if(admin.isPresent())
            return new ResponseEntity<>(
                    "l'email existe déjà",
                    HttpStatus.CONFLICT
            );
        return null;
    }

    private ResponseEntity<?> userExisteResponse(User entity){
        Optional<User> user = userService.getUserByEmail(entity.getEmail());
        if(user.isPresent())
            return new ResponseEntity<>(
                    "l'email existe déjà",
                    HttpStatus.CONFLICT
            );
        return null;
    }

    @PostMapping("signup")
    public ResponseEntity<?> adminRegister(@Valid @RequestBody Admin entity){
        ResponseEntity<?> res = adminExisteResponse(entity);
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

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody Map<String, String> request){
        String email = request.get("email");
        String password = request.get("password");
        Optional<Admin> admin = adminService.getAdminByEmail(email);
        if (admin.isEmpty())
            return new ResponseEntity(
                    "Admin n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        String jwt = authService.loginAdmin(admin.get(), password);
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

    @PostMapping("users")
    public ResponseEntity<?> userRegister(@Valid @RequestBody User entity){
        ResponseEntity<?> res = userExisteResponse(entity);

        if (res != null)
            return res;
        Optional<Role> role = roleRepo.findByRoleName(Role.RoleEnum.USER.name());
        if(role.isEmpty()){
            return new ResponseEntity<>(
                    "Une erreur est servenue !",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(
                authService.registerUser(entity, role.get()),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        Optional<User> user = userService.getUserById(id);
        if(user.isEmpty())
            return new ResponseEntity<>(
                    "User n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        userService.deleteUser(user.get());
        return new ResponseEntity<>(
                "User supprimé",
                HttpStatus.OK
        );
    }

    @PutMapping("users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User entity){
        Optional<User> user = userService.getUserById(id);
        if(user.isEmpty())
            return new ResponseEntity<>(
                    "User n'existe pas",
                    HttpStatus.NOT_FOUND
            );
        return new ResponseEntity<>(
                userService.updateUser(user.get(), entity),
                HttpStatus.OK
        );
    }
}
