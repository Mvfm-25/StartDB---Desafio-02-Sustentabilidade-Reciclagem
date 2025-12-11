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

    @Autowired
    private UfRepository ufRepository;

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
        .filter(c -> c.getTiposColeta() != null && 
                     c.getTiposColeta().stream()
                      .anyMatch(t -> t.getNome().equalsIgnoreCase(tipo)))
        .toList();
    }

    // Normaliza string removendo acentos e espaços
    private String normalizarString(String str) {
        if (str == null) return null;
        return str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

    // Retorna todos os pontos de uma cidade específica
    public List<PontoColeta> getPontosPorCidade(String nomeCidade){
        String nomeCidadeNormalizado = normalizarString(nomeCidade);
        Cidade cidade = cidadeRepository.findAll().stream()
        .filter(c -> normalizarString(c.getNome()).equals(nomeCidadeNormalizado))
        .findFirst()
        .orElse(null);
        
        if(cidade == null || cidade.getPontos() == null){
            return null;
        }
        return cidade.getPontos();
    }

    // Adiciona um novo ponto a uma cidade (cria a cidade se não existir)
    public PontoColeta adicionarPontoACidade(String nomeCidade, PontoColeta novoPonto){
        String nomeCidadeNormalizado = normalizarString(nomeCidade);
        Cidade cidade = cidadeRepository.findAll().stream()
        .filter(c -> normalizarString(c.getNome()).equals(nomeCidadeNormalizado))
        .findFirst()
        .orElse(null);
        
        // Se a cidade não existir, cria uma nova
        if(cidade == null){
            cidade = new Cidade();
            // Se o nome vier sem acentos/espaços, adiciona de forma mais amigável
            String nomeFormatado = nomeCidade.replaceAll("([a-z])([A-Z])", "$1 $2");
            cidade.setNome(nomeFormatado.length() > 0 ? nomeFormatado : nomeCidade);
            cidade.setPontos(new java.util.ArrayList<>());
            // Tenta usar uma UF padrão ou cria uma genérica
            Uf ufPadrao = ufRepository.findAll().stream().findFirst().orElse(null);
            if(ufPadrao == null){
                ufPadrao = new Uf();
                ufPadrao.setSigla("SP");
                ufPadrao.setNome("São Paulo");
                ufPadrao = ufRepository.save(ufPadrao);
            }
            cidade.setUf(ufPadrao);
            cidade = cidadeRepository.save(cidade);
        }
        
        // Salva o novo ponto no banco
        PontoColeta pontoSalvo = pontoColetaRepository.save(novoPonto);
        
        // Adiciona à lista de pontos da cidade
        if(cidade.getPontos() == null){
            cidade.setPontos(new java.util.ArrayList<>());
        }
        cidade.getPontos().add(pontoSalvo);
        cidadeRepository.save(cidade);
        
        return pontoSalvo;
    }

    // Retorna pontos de um bairro específico
    public List<PontoColeta> getPontosPorBairro(String nomeBairro){
        return pontoColetaRepository.findAll().stream()
        .filter(p -> p.getEndereco() != null && p.getEndereco().getBairro() != null && p.getEndereco().getBairro().equalsIgnoreCase(nomeBairro))
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

    // Retorna pontos que funcionam em um dia da semana específico
    public List<PontoColeta> getPontosPorDiaSemana(String diaSemana){
        return pontoColetaRepository.findAll().stream()
        .filter(p -> p.getHorariosFunc() != null && 
                     p.getHorariosFunc().stream().anyMatch(h -> 
                         h.getDiaSemana() != null && 
                         (h.getDiaSemana().equalsIgnoreCase(diaSemana) || 
                          h.getDiaSemana().toUpperCase().equals(diaSemana.toUpperCase()))))
        .toList();
    }

    // Retorna pontos que funcionam HOJE
    public List<PontoColeta> getPontosFuncionandoHoje(){
        DiaSemana hoje = DiaSemana.getHoje();
        return getPontosPorDiaSemana(hoje.name());
    }

    // Retorna pontos que funcionam em um horário específico (ex: agora)
    public List<PontoColeta> getPontosAbertosAgora(){
        java.time.LocalTime agora = java.time.LocalTime.now();
        DiaSemana hoje = DiaSemana.getHoje();
        
        return pontoColetaRepository.findAll().stream()
        .filter(p -> p.getHorariosFunc() != null && 
                     p.getHorariosFunc().stream().anyMatch(h -> 
                         h.getDiaSemana() != null &&
                         (h.getDiaSemana().equalsIgnoreCase(hoje.name()) || 
                          h.getDiaSemana().equalsIgnoreCase(hoje.getNomePortugues())) &&
                         isHorarioAberto(h, agora)))
        .toList();
    }

    // Método auxiliar para verificar se um horário está aberto
    private boolean isHorarioAberto(HorarioFuncionamento horario, java.time.LocalTime hora){
        try {
            java.time.LocalTime abertura = java.time.LocalTime.parse(horario.getHoraAbertura());
            java.time.LocalTime fechamento = java.time.LocalTime.parse(horario.getHoraFechamento());
            return !hora.isBefore(abertura) && hora.isBefore(fechamento);
        } catch (Exception e) {
            return false;
        }
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

    // Valida e obtém UF e Cidade a partir dos dados do frontend
    // Se não existirem, cria novos registros
    public EnderecoComCidade validarEOuCriarUfECidade(String nomeCidade, String siglaUf){
        // Busca ou cria o Estado (UF)
        Uf uf = ufRepository.findBySigla(siglaUf.toUpperCase()).orElse(null);
        if(uf == null){
            // Se não existe, cria um novo estado
            uf = new Uf();
            uf.setSigla(siglaUf.toUpperCase());
            uf.setNome(siglaUf); // Pode ser melhorado com um mapa de siglas -> nomes completos
            uf = ufRepository.save(uf);
        }

        // Busca ou cria a Cidade
        Cidade cidade = cidadeRepository.findByNomeAndUfId(nomeCidade, uf.getId()).orElse(null);
        if(cidade == null){
            // Se não existe, cria uma nova cidade
            cidade = new Cidade();
            cidade.setNome(nomeCidade);
            cidade.setUf(uf);
            cidade.setPontos(new java.util.ArrayList<>()); // Inicializa lista vazia de pontos
            cidade = cidadeRepository.save(cidade);
        }

        // Retorna o DTO com os dados validados/criados
        return new EnderecoComCidade(null, cidade, uf);
    }

    // Retorna todas as cidades
    public List<Cidade> getTodosCidades(){
        return cidadeRepository.findAll();
    }

    // Retorna todas as UFs
    public List<Uf> getTodosUfs(){
        return ufRepository.findAll();
    }

    // Busca cidades por UF
    public List<Cidade> getCidadesPorUf(String siglaUf){
        Uf uf = ufRepository.findBySigla(siglaUf.toUpperCase()).orElse(null);
        if(uf == null){
            return null;
        }
        return cidadeRepository.findByUfId(uf.getId());
    }

    // Busca uma cidade por nome
    public Cidade getCidadePorNome(String nome){
        return cidadeRepository.findAll().stream()
        .filter(c -> c.getNome().equalsIgnoreCase(nome))
        .findFirst()
        .orElse(null);
    }

    // Busca uma UF por sigla
    public Uf getUfPorSigla(String sigla){
        return ufRepository.findBySigla(sigla.toUpperCase()).orElse(null);
    }
}
