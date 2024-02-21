package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatRepository extends JpaRepository<Etat, Long> {
    public Optional<Etat> findByLibelle(String libelle);

    @Query(value = "SELECT case when count(e)>0 then true else false end FROM Etat e where e.libelle = :libelle")
    public Boolean existByLibelle(String libelle);
}
