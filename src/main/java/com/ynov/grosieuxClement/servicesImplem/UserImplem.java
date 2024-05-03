package com.ynov.grosieuxClement.servicesImplem;

import com.ynov.grosieuxClement.models.Role;
import com.ynov.grosieuxClement.models.User;
import com.ynov.grosieuxClement.repositories.UserRepo;
import com.ynov.grosieuxClement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserImplem implements UserService {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Override
    public User createUser(User entity) {
        return userRepo.save(entity);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Override
    public User updateUser(User userToUpdate, User user) {
        // Vérifier si l'utilisateur existe
        if (userToUpdate == null) {
            throw new IllegalArgumentException("L'utilisateur à mettre à jour ne peut pas être null");
        }

        // Mettre à jour les attributs de l'utilisateur
        if (user.getEmail() != null) {
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            // Encoder le mot de passe avant de le mettre à jour
            String passwordEncoded = bCryptPasswordEncoder.encode(user.getPassword());
            userToUpdate.setPassword(passwordEncoded);
        }

        // Sauvegarder l'utilisateur mis à jour
        return userRepo.save(userToUpdate);
    }

    @Override
    public Optional<User> getUserByValidationCode(String code) {
        // convert code to int
        int codeInt = Integer.parseInt(code);
        return userRepo.findByCode(codeInt);

    }


}
