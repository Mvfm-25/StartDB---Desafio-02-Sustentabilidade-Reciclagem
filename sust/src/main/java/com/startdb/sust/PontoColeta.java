// Classe principal para a definição de um ponto de coleta, onde um ponto pode haver inúmeras coletas a serem efetuadas, em progresso & completas.
// Nada muito complicado por enquanto, baseando-me na ideia 2 que foi sugerida, um simples catálogo de Pontos de Coleta.
// [mvfm]
//
// Criado : 23/11/2025  || Última modificação : 23/11/2025

package com.startdb.sust;

public class PontoColeta {

    private long id;
    // Bastante coisa sendo salva como String por enquanto, acredito que seja algo para refinar à diante.
    private TipoColeta tipoColeta;
    private String nomePonto, endereco, desc, horarioFunc;
     
    public PontoColeta(){}

    // Receita de bolo.
    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getNomePonto(){return nomePonto;}
    public void setNomePonto(String nomePonto){this.nomePonto = nomePonto;}

    public TipoColeta getTipoColeta(){return tipoColeta;}
    public void setTipoColeta(TipoColeta tipoColeta){this.tipoColeta = tipoColeta;}

    public String getEndereco(){return endereco;}
    public void setEndereco(String endereco){this.endereco = endereco;}

    public String getDesc(){return desc;}
    public void setDesc(String desc){this.desc = desc;}

    public String getHorarioFunc(){return horarioFunc;}
    public void set(String horarioFunc){this.horarioFunc = horarioFunc;}

}
