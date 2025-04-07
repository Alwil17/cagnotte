package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT case when count(c)>0 then true else false end from Category c where c.label = :libelle")
    boolean existsByLibelleEquals ( String libelle );

    @Query("SELECT c FROM Category c where c.label = :libelle")
    Optional<Category> findByLibelle (String libelle);
}
