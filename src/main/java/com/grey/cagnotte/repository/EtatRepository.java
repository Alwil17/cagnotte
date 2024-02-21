package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatRepository extends JpaRepository<Etat, Long> {

    @Query(value = "SELECT E FROM Etat E where E.libelle= :libelle")
    public Optional<Etat> findByLibelle(final String libelle);

    @Query(value = "SELECT case when count(e)>0 then true else false end FROM Etat e where libelle= :libelle")
    public Boolean existByLibelle(final String libelle);
}
