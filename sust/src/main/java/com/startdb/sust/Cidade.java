// Onde coletamos (huh?) todos os pontos de coleta. De novo, nada complexo.
// [mvfm]
//
// Criado : 23/11/2025  || Última modificação : 23/11/2025

package com.startdb.sust;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;

@Entity
public class Cidade {
    // Podemos fazer com que cada cidade mantenha seus próprios pontos para não termos algo muito centralizado.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    @ManyToOne
    private Uf uf;
    @OneToMany
    private List<PontoColeta> pontos;

    // Novamente, receita de bolo.
    public Cidade(){}

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}

    public Uf getUf(){return uf;}
    public void setUf(Uf uf){this.uf = uf;}

    public List<PontoColeta> getPontos(){return pontos;}
    public void setPontos(List<PontoColeta> pontos){this.pontos = pontos;}
}
