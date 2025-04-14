package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Cagnotte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CagnotteRepository extends JpaRepository<Cagnotte,Long> {

    Optional<Cagnotte> findBySlug(String slug);

    @Query("select c from Cagnotte c where c.isPublic = true")
    List<Cagnotte> findAllByPublicTrue();

    @Query("select c from Cagnotte c where c.user.id = :userId")
    List<Cagnotte> findAllMyCagnotte(long userId);
}
