package com.ynov.grosieuxClement.servicesImplem;

import com.ynov.grosieuxClement.models.Admin;
import com.ynov.grosieuxClement.models.Role;
import com.ynov.grosieuxClement.repositories.AdminRepo;
import com.ynov.grosieuxClement.repositories.UserRepo;
import com.ynov.grosieuxClement.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminImplem implements AdminService {
    @Autowired
    AdminRepo adminRepo;

    @Override
    public Optional<Admin> getAdminByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void addRoleToAdmin(Admin admin, Role role) {
        admin.addRole(role);
        adminRepo.save(admin);
    }

    @Override
    public Admin createAdmin(Admin entity) {
        return adminRepo.save(entity);
    }
}
