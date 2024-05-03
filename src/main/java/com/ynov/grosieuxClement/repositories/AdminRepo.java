package com.ynov.grosieuxClement.repositories;

import com.ynov.grosieuxClement.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin save(Admin user);
}