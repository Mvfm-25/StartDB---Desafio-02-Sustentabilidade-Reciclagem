// Classe de serviço para os pontos de coleta. Principal meio de comunicação.
// [mvfm]
//
// Criado : 23/11/2025  || Última modificação : 23/11/2025

package com.startdb.sust;

import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Service
public class ColetaService {
    // Trazendo o essencial.
    private Pontos pontos;
    private final ObjectMapper objMapper = new ObjectMapper();

    // Retorna todos os pontos de Pontos.java
    public List<PontoColeta> getTudo(){return pontos.getPontos();}

    // Retorna um ponto específico por nome.
    public PontoColeta getPontoPorNome(String nome){
        if(pontos == null){return null;}
        return pontos.getPontos().stream()
        .filter(c -> c.getNomePonto().equalsIgnoreCase(nome))
        .findFirst()
        .orElse(null);
    }

    // Retorna uma lista de pontos de coleta, baseando-se no tipo de coleta sendo feita lá.
    public List<PontoColeta> getPontoPorColeta(String tipo){
        if(pontos == null){return null;}
        return pontos.getPontos().stream()
        .filter(c -> c.getTipoColeta().getNome().equalsIgnoreCase(tipo))
        .toList();
    }
}
