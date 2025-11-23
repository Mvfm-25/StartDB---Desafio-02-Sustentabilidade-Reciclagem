package com.startdb.sust;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ColetaController {
    
    @Autowired
    private ColetaService coletaService;

    @GetMapping("/pontos")
    public String mostraPontos(Model modelo){
        modelo.addAttribute("Pontos de coleta", coletaService.getTudo());
        // Nome do futuro arquivo html.
        return "pontos";
    }
}
