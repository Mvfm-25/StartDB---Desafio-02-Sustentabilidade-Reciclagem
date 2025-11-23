package com.startdb.sust;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoColetaRepository extends JpaRepository<TipoColeta, Long> {
}
