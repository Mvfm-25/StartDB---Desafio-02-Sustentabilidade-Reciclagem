// Classe de serviço para os pontos de coleta. Principal meio de comunicação.
// [mvfm]
//
// Criado : 23/11/2025  || Última modificação : 23/11/2025

package com.startdb.sust;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ColetaService {
    @Autowired
    private CidadeRepository cidadeRepository;
    
    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    // Retorna todos os pontos de Pontos.java
    public List<PontoColeta> getTudo(){
        return pontoColetaRepository.findAll();
    }

    // Retorna um ponto específico por nome.
    public PontoColeta getPontoPorNome(String nome){
        return pontoColetaRepository.findAll().stream()
        .filter(c -> c.getNomePonto().equalsIgnoreCase(nome))
        .findFirst()
        .orElse(null);
    }

    // Retorna uma lista de pontos de coleta, baseando-se no tipo de coleta sendo feita lá.
    public List<PontoColeta> getPontoPorColeta(String tipo){
        return pontoColetaRepository.findAll().stream()
        .filter(c -> c.getTipoColeta() != null && c.getTipoColeta().getNome().equalsIgnoreCase(tipo))
        .toList();
    }

    // Retorna todos os pontos de uma cidade específica
    public List<PontoColeta> getPontosPorCidade(String nomeCidade){
        Cidade cidade = cidadeRepository.findAll().stream()
        .filter(c -> c.getNomeCidade().equalsIgnoreCase(nomeCidade))
        .findFirst()
        .orElse(null);
        
        if(cidade == null || cidade.getPontos() == null){
            return null;
        }
        return cidade.getPontos();
    }

    // Adiciona um novo ponto a uma cidade
    public PontoColeta adicionarPontoACidade(String nomeCidade, PontoColeta novoPonto){
        Cidade cidade = cidadeRepository.findAll().stream()
        .filter(c -> c.getNomeCidade().equalsIgnoreCase(nomeCidade))
        .findFirst()
        .orElse(null);
        
        if(cidade == null){
            return null;
        }
        
        // Salva o novo ponto no banco
        PontoColeta pontoSalvo = pontoColetaRepository.save(novoPonto);
        
        // Adiciona à lista de pontos da cidade
        cidade.getPontos().add(pontoSalvo);
        cidadeRepository.save(cidade);
        
        return pontoSalvo;
    }
}
