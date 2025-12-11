// Classe para representação de Horários de funcionamento de um ponto de coleta.
// Isso resolve as nossas complicações de como guardar tal informação.
// [mvfm]
//
// Criado : 24/11/2025  || Última modificação : 24/11/2025

package com.startdb.sust;

import jakarta.persistence.Embeddable;

@Embeddable
public class HorarioFuncionamento {
    private String diaSemana;      // Ex: "SEGUNDA", "TERÇA", etc (usar enum DiaSemana)
    private String horaAbertura;   // Ex: "08:00"
    private String horaFechamento; // Ex: "18:00"
    private boolean funcionaFeriado; // Se funciona em feriados

    public HorarioFuncionamento(){}

    public HorarioFuncionamento(String diaSemana, String horaAbertura, String horaFechamento, boolean funcionaFeriado){
        this.diaSemana = diaSemana;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
        this.funcionaFeriado = funcionaFeriado;
    }

    // Getters e Setters
    public String getDiaSemana(){return diaSemana;}
    public void setDiaSemana(String diaSemana){this.diaSemana = diaSemana;}
    
    // Método auxiliar para obter o Enum do dia
    public DiaSemana getDiaSemanaEnum(){
        try {
            return DiaSemana.valueOf(diaSemana);
        } catch (IllegalArgumentException e) {
            return DiaSemana.porNomePortugues(diaSemana);
        }
    }

    public String getHoraAbertura(){return horaAbertura;}
    public void setHoraAbertura(String horaAbertura){this.horaAbertura = horaAbertura;}

    public String getHoraFechamento(){return horaFechamento;}
    public void setHoraFechamento(String horaFechamento){this.horaFechamento = horaFechamento;}

    public boolean isFuncionaFeriado(){return funcionaFeriado;}
    public void setFuncionaFeriado(boolean funcionaFeriado){this.funcionaFeriado = funcionaFeriado;}

    // Método auxiliar para retornar horário formatado
    public String getHorarioFormatado(){
        return diaSemana + ": " + horaAbertura + " - " + horaFechamento + 
               (funcionaFeriado ? " (Funciona feriado)" : "");
    }
}
