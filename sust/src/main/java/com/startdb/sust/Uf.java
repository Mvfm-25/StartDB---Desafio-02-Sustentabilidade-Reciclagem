// Classe para representação de Estados.
// Nada além disso por enquanto.
// [mvfm]
//
// Criado : 24/11/2025  || Última modificação : 24/11/2025

package com.startdb.sust;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
public class Uf {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;      // Ex: "São Paulo"
    private String sigla;     // Ex: "SP"
    @OneToMany(mappedBy = "uf", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cidade> cidades;

    public Uf(){}

    public Uf(String nome, String sigla){
        this.nome = nome;
        this.sigla = sigla;
    }

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}

    public String getSigla(){return sigla;}
    public void setSigla(String sigla){this.sigla = sigla;}

    public List<Cidade> getCidades(){return cidades;}
    public void setCidades(List<Cidade> cidades){this.cidades = cidades;}

    @Override
    public String toString(){
        return sigla;
    }
}
