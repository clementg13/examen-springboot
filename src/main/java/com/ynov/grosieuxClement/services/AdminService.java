package com.ynov.grosieuxClement.services;

import com.ynov.grosieuxClement.models.Admin;
import com.ynov.grosieuxClement.models.Role;

import java.util.Optional;

public interface AdminService {
    Optional<Admin> getAdminByEmail(String email);
    void addRoleToAdmin(Admin admin, Role role);
    Admin createAdmin(Admin entity);
}
