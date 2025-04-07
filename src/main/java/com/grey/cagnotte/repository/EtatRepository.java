package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtatRepository extends JpaRepository<State, Long> {
    public Optional<State> findByLibelle(String libelle);

    @Query(value = "SELECT case when count(e)>0 then true else false end FROM State e where e.label = :libelle")
    public Boolean existByLibelle(String libelle);
}
