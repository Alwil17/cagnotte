package com.grey.cagnotte.repository;

import com.grey.cagnotte.entity.Cagnotte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CagnotteRepository extends JpaRepository<Cagnotte,Long> {

}
