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

    // Retorna pontos de um bairro específico
    public List<PontoColeta> getPontosPorBairro(String nomeBairro){
        return pontoColetaRepository.findAll().stream()
        .filter(p -> p.getEndereco() != null && p.getEndereco().getBairro().equalsIgnoreCase(nomeBairro))
        .toList();
    }

    // Retorna pontos por CEP
    public List<PontoColeta> getPontosPorCep(String cep){
        return pontoColetaRepository.findAll().stream()
        .filter(p -> p.getEndereco() != null && p.getEndereco().getCep().equalsIgnoreCase(cep))
        .toList();
    }

    // Retorna pontos por rua
    public List<PontoColeta> getPontosPorRua(String nomeRua){
        return pontoColetaRepository.findAll().stream()
        .filter(p -> p.getEndereco() != null && p.getEndereco().getRua().equalsIgnoreCase(nomeRua))
        .toList();
    }

    // Atualiza o endereço de um ponto
    public PontoColeta atualizarEndereco(long id, Endereco novoEndereco){
        PontoColeta ponto = pontoColetaRepository.findById(id).orElse(null);
        if(ponto == null){
            return null;
        }
        ponto.setEndereco(novoEndereco);
        return pontoColetaRepository.save(ponto);
    }

    // Retorna um ponto específico por ID
    public PontoColeta getPontoPorId(long id){
        return pontoColetaRepository.findById(id).orElse(null);
    }

    // Deleta um ponto por ID
    public boolean deletarPonto(long id){
        if(pontoColetaRepository.existsById(id)){
            pontoColetaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Adiciona um novo horário de funcionamento a um ponto
    public PontoColeta adicionarHorarioFuncionamento(long id, HorarioFuncionamento novoHorario){
        PontoColeta ponto = pontoColetaRepository.findById(id).orElse(null);
        if(ponto == null){
            return null;
        }
        if(ponto.getHorariosFunc() == null){
            ponto.setHorariosFunc(new java.util.ArrayList<>());
        }
        ponto.getHorariosFunc().add(novoHorario);
        return pontoColetaRepository.save(ponto);
    }

    // Retorna todos os horários de funcionamento de um ponto
    public List<HorarioFuncionamento> getHorariosPonto(long id){
        PontoColeta ponto = pontoColetaRepository.findById(id).orElse(null);
        if(ponto == null){
            return null;
        }
        return ponto.getHorariosFunc();
    }

    // Atualiza todos os horários de funcionamento de um ponto
    public PontoColeta atualizarHorarios(long id, List<HorarioFuncionamento> novosHorarios){
        PontoColeta ponto = pontoColetaRepository.findById(id).orElse(null);
        if(ponto == null){
            return null;
        }
        ponto.setHorariosFunc(novosHorarios);
        return pontoColetaRepository.save(ponto);
    }

    // Deleta um horário específico de um ponto pelo índice
    public PontoColeta deletarHorario(long id, int indice){
        PontoColeta ponto = pontoColetaRepository.findById(id).orElse(null);
        if(ponto == null || ponto.getHorariosFunc() == null || indice < 0 || indice >= ponto.getHorariosFunc().size()){
            return null;
        }
        ponto.getHorariosFunc().remove(indice);
        return pontoColetaRepository.save(ponto);
    }
}
