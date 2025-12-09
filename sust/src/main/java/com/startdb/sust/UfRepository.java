package com.startdb.sust;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UfRepository extends JpaRepository<Uf, Long> {
    Optional<Uf> findBySigla(String sigla);
}
