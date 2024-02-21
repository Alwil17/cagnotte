package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    @Query("SELECT case when count(c)>0 then true else false end from Categorie c where c.libelle = :libelle")
    boolean existsByLibelleEquals ( String libelle );

    @Query("SELECT c FROM Categorie c where c.libelle = :libelle")
    Optional<Categorie> findByLibelle (String libelle);
}
