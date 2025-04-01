package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByTitre(String titre);
}
