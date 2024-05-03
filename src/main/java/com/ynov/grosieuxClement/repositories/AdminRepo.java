package com.ynov.grosieuxClement.repositories;

import com.ynov.grosieuxClement.models.Admin;
import com.ynov.grosieuxClement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin save(Admin user);
    Optional<Admin> findByEmail(String email);
}