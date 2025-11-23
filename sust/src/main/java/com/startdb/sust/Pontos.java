// Onde coletamos (huh?) todos os pontos de coleta. De novo, nada complexo.
// [mvfm]
//
// Criado : 23/11/2025  || Última modificação : 23/11/2025

package com.startdb.sust;
import java.util.List;

public class Pontos {
    // Podemos fazer com que cada cidade mantenha seus próprios pontos para não termos algo muito centralizado.
    private long id;
    private String nomeCidade;
    private List<PontoColeta> pontos;

    // Novamente, receita de bolo.
    public Pontos(){}

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getNomeCidade(){return nomeCidade;}
    public void setNomeCidade(String nomeCidade){this.nomeCidade = nomeCidade;}

    public List<PontoColeta> getPontos(){return pontos;}
    public void setPontos(List<PontoColeta> pontos){this.pontos = pontos;}
}
