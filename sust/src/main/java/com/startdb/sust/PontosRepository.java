package com.startdb.sust;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontosRepository extends JpaRepository<Pontos, Long> {
}
