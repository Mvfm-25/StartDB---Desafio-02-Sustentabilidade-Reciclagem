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

@Entity
public class Uf {
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    
}
