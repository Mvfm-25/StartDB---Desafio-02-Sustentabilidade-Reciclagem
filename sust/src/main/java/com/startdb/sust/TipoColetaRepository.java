package com.startdb.sust;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TipoColetaRepository extends JpaRepository<TipoColeta, Long> {
    
    // NOVO: Método para buscar um TipoColeta pelo nome, ignorando maiúsculas/minúsculas
    Optional<TipoColeta> findByNomeIgnoreCase(String nome);
}