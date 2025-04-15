package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {

    @Query("SELECT p from Participation p where p.cagnotte.url = :cUrl")
    List<Participation> findAllByCagnotteUrl(@Param("cUrl") String cagnotteUrl);

    int countByCagnotteId(long cagnotteId);
}