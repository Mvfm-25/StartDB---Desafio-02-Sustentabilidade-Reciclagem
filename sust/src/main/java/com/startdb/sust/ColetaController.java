package com.startdb.sust;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ColetaController {
    
    @Autowired
    private ColetaService coletaService;

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

    // POST novo ponto em uma cidade
    @PostMapping("/cidades/{nomeCidade}/pontos")
    public ResponseEntity<PontoColeta> adicionarPonto(@PathVariable String nomeCidade, @RequestBody PontoColeta novoPonto){
        PontoColeta pontoSalvo = coletaService.adicionarPontoACidade(nomeCidade, novoPonto);
        if(pontoSalvo == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pontoSalvo);
    }
}
