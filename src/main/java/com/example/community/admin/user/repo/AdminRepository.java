package com.example.community.admin.user.repo;

import com.example.community.admin.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByAdminId(String adminId);

}