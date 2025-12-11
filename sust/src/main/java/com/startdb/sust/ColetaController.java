package com.startdb.sust;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "5173", maxAge = 3600)
public class ColetaController {
    
    @Autowired
    private ColetaService coletaService;

    @Autowired
    private ViaCepService viaCepService;

    // GET todos os pontos
    @GetMapping("/pontos")
    public ResponseEntity<List<PontoColeta>> getTodosPontos(){
        return ResponseEntity.ok(coletaService.getTudo());
    }

    // GET pontos de uma cidade específica
    @GetMapping("/cidades/{nomeCidade}/pontos")
    public ResponseEntity<List<PontoColeta>> getPontosPorCidade(@PathVariable String nomeCidade){
        List<PontoColeta> pontos = coletaService.getPontosPorCidade(nomeCidade);
        if(pontos == null || pontos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontos);
    }

    // GET pontos de um bairro específico
    @GetMapping("/bairros/{nomeBairro}/pontos")
    public ResponseEntity<List<PontoColeta>> getPontosPorBairro(@PathVariable String nomeBairro){
        List<PontoColeta> pontos = coletaService.getPontosPorBairro(nomeBairro);
        if(pontos == null || pontos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontos);
    }

    // GET pontos por CEP
    @GetMapping("/ceps/{cep}/pontos")
    public ResponseEntity<List<PontoColeta>> getPontosPorCep(@PathVariable String cep){
        List<PontoColeta> pontos = coletaService.getPontosPorCep(cep);
        if(pontos == null || pontos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontos);
    }

    // GET pontos por rua
    @GetMapping("/ruas/{nomeRua}/pontos")
    public ResponseEntity<List<PontoColeta>> getPontosPorRua(@PathVariable String nomeRua){
        List<PontoColeta> pontos = coletaService.getPontosPorRua(nomeRua);
        if(pontos == null || pontos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontos);
    }

    // GET pontos por tipo de coleta
    @GetMapping("/coletas/{tipo}")
    public ResponseEntity<List<PontoColeta>> getPontosPorTipoColeta(@PathVariable String tipo){
        List<PontoColeta> pontos = coletaService.getPontoPorColeta(tipo);
        if(pontos == null || pontos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontos);
    }

    // GET pontos que funcionam em um dia da semana específico
    @GetMapping("/dias-semana/{diaSemana}/pontos")
    public ResponseEntity<List<PontoColeta>> getPontosPorDiaSemana(@PathVariable String diaSemana){
        List<PontoColeta> pontos = coletaService.getPontosPorDiaSemana(diaSemana);
        if(pontos == null || pontos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontos);
    }

    // GET pontos que funcionam HOJE
    @GetMapping("/pontos/hoje")
    public ResponseEntity<List<PontoColeta>> getPontosFuncionandoHoje(){
        List<PontoColeta> pontos = coletaService.getPontosFuncionandoHoje();
        if(pontos == null || pontos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontos);
    }

    // GET pontos que estão ABERTOS AGORA
    @GetMapping("/pontos/abertos-agora")
    public ResponseEntity<List<PontoColeta>> getPontosAbertosAgora(){
        List<PontoColeta> pontos = coletaService.getPontosAbertosAgora();
        if(pontos == null || pontos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontos);
    }

    // POST novo ponto em uma cidade
    @PostMapping("/cidades/{nomeCidade}/pontos")
    public ResponseEntity<PontoColeta> adicionarPonto(@PathVariable String nomeCidade, @RequestBody PontoColeta novoPonto){
        PontoColeta pontoSalvo = coletaService.adicionarPontoACidade(nomeCidade, novoPonto);
        if(pontoSalvo == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pontoSalvo);
    }

    // PUT atualizar endereço de um ponto
    @PutMapping("/pontos/{id}/endereco")
    public ResponseEntity<PontoColeta> atualizarEndereco(@PathVariable long id, @RequestBody Endereco novoEndereco){
        PontoColeta pontoAtualizado = coletaService.atualizarEndereco(id, novoEndereco);
        if(pontoAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontoAtualizado);
    }

    // POST adicionar novo horário de funcionamento a um ponto
    @PostMapping("/pontos/{id}/horarios")
    public ResponseEntity<PontoColeta> adicionarHorario(@PathVariable long id, @RequestBody HorarioFuncionamento novoHorario){
        PontoColeta pontoAtualizado = coletaService.adicionarHorarioFuncionamento(id, novoHorario);
        if(pontoAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pontoAtualizado);
    }

    // GET horários de funcionamento de um ponto
    @GetMapping("/pontos/{id}/horarios")
    public ResponseEntity<List<HorarioFuncionamento>> getHorariosPonto(@PathVariable long id){
        List<HorarioFuncionamento> horarios = coletaService.getHorariosPonto(id);
        if(horarios == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(horarios);
    }

    // PUT atualizar horários de um ponto (substituir todos)
    @PutMapping("/pontos/{id}/horarios")
    public ResponseEntity<PontoColeta> atualizarHorarios(@PathVariable long id, @RequestBody List<HorarioFuncionamento> novosHorarios){
        PontoColeta pontoAtualizado = coletaService.atualizarHorarios(id, novosHorarios);
        if(pontoAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontoAtualizado);
    }

    // DELETE horário específico de um ponto
    @DeleteMapping("/pontos/{id}/horarios/{indice}")
    public ResponseEntity<PontoColeta> deletarHorario(@PathVariable long id, @PathVariable int indice){
        PontoColeta pontoAtualizado = coletaService.deletarHorario(id, indice);
        if(pontoAtualizado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pontoAtualizado);
    }

    // GET ponto por ID
    @GetMapping("/pontos/{id}")
    public ResponseEntity<PontoColeta> getPontoPorId(@PathVariable long id){
        PontoColeta ponto = coletaService.getPontoPorId(id);
        if(ponto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ponto);
    }

    // DELETE ponto por ID
    @DeleteMapping("/pontos/{id}")
    public ResponseEntity<Void> deletarPonto(@PathVariable long id){
        boolean deletado = coletaService.deletarPonto(id);
        if(!deletado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // GET informações de endereço pelo CEP (ViaCEP)
    @GetMapping("/viacep/{cep}")
    public ResponseEntity<ViaCepResponse> buscarCep(@PathVariable String cep){
        ViaCepResponse response = viaCepService.buscarPorCep(cep);
        if(response == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    // POST criar endereço a partir de um CEP
    @PostMapping("/endereco-por-cep")
    public ResponseEntity<Endereco> criarEnderecoPorCep(@RequestParam String cep, @RequestParam String numero, @RequestParam(required = false) String complemento){
        Endereco endereco = viaCepService.criarEnderecoAPartirDeCep(cep, numero, complemento);
        if(endereco == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(endereco);
    }

    // POST criar endereço com cidade e UF integrados
    @PostMapping("/endereco-completo-por-cep")
    public ResponseEntity<EnderecoComCidade> criarEnderecoCompletoParaoCep(@RequestParam String cep, @RequestParam String numero, @RequestParam(required = false) String complemento){
        EnderecoComCidade endereco = viaCepService.criarEnderecoComCidade(cep, numero, complemento);
        if(endereco == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(endereco);
    }

    // POST validar e obter UF e Cidade (cria se não existir)
    @PostMapping("/validar-uf-cidade")
    public ResponseEntity<EnderecoComCidade> validarUfECidade(@RequestParam String cidade, @RequestParam String uf){
        EnderecoComCidade resultado = coletaService.validarEOuCriarUfECidade(cidade, uf);
        if(resultado == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resultado);
    }
}
