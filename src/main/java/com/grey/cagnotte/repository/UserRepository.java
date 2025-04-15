package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT case when count(u)>0 then true else false end from User u where u.email = :email")
    boolean existsByEmailEquals(String email);

    @Query("SELECT u from User u where u.email = :email")
    Optional<User> findByEmailEquals(String email);

    Optional<User> findByUsername(String username);

    // Fetch all emails for users that have a role with the specified name
    @Query("SELECT u.email FROM User u JOIN u.roles r WHERE r.name IN ('ADMIN', 'SUPERADMIN')")
    List<String> findAllAdminEmails();
}