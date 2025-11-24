// Classe principal para a definição de um ponto de coleta, onde um ponto pode haver inúmeras coletas a serem efetuadas, em progresso & completas.
// Nada muito complicado por enquanto, baseando-me na ideia 2 que foi sugerida, um simples catálogo de Pontos de Coleta.
// [mvfm]
//
// Criado : 23/11/2025  || Última modificação : 23/11/2025

package com.startdb.sust;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Embedded;
import jakarta.persistence.ElementCollection;
import java.util.List;

@Entity
public class PontoColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany
    private TipoColeta tipoColeta;
    private String nomePonto;
    @Embedded
    private Endereco endereco;
    private String desc;
    @ElementCollection
    private List<HorarioFuncionamento> horariosFunc;
     
    public PontoColeta(){}

    // Receita de bolo.
    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getNomePonto(){return nomePonto;}
    public void setNomePonto(String nomePonto){this.nomePonto = nomePonto;}

    public TipoColeta getTipoColeta(){return tipoColeta;}
    public void setTipoColeta(TipoColeta tipoColeta){this.tipoColeta = tipoColeta;}

    public Endereco getEndereco(){return endereco;}
    public void setEndereco(Endereco endereco){this.endereco = endereco;}

    public String getDesc(){return desc;}
    public void setDesc(String desc){this.desc = desc;}

    public List<HorarioFuncionamento> getHorariosFunc(){return horariosFunc;}
    public void setHorariosFunc(List<HorarioFuncionamento> horariosFunc){this.horariosFunc = horariosFunc;}

}
