package com.startdb.sust;

// Importar a classe List para o Controller
import java.util.List;

// DTO para representar a Cidade com a UF, excluindo a lista de Pontos.
public class CidadeSimplesDto {
    private long id;
    private String nome;
    private Uf uf;

    // Construtor que recebe a entidade Cidade e a mapeia para o DTO
    public CidadeSimplesDto(Cidade cidade) {
        this.id = cidade.getId();
        this.nome = cidade.getNome();
        this.uf = cidade.getUf();
        // A lista de pontos (pontos) é intencionalmente omitida aqui.
    }

    // Getters e Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Uf getUf() { return uf; }
    public void setUf(Uf uf) { this.uf = uf; }
}