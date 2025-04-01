package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT case when count(u)>0 then true else false end from User u where u.email = :email")
    boolean existsByEmailEquals(String email);

    @Query("SELECT u from User u where u.email = :email")
    Optional<User> findByEmailEquals(String email);

    Optional<User> findByUsername(String username);
}