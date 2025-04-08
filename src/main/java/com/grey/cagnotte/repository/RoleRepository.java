package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    boolean existsByNameEquals(String name);
}
