// Classe para DTO entre ViaCEP API & nosso projeto.
// [mvfm]
//
// Criado : 24/11/2025  || Última modificação : 24/11/2025

package com.startdb.sust;

// DTO para agrupar : Endereco, Cidade & UF
// Ajuda pra retornar o que recebemos de ViaCEP.
public class EnderecoComCidade {
    private Endereco endereco;
    private Cidade cidade;
    private Uf uf;

    public EnderecoComCidade(){}

    public EnderecoComCidade(Endereco endereco, Cidade cidade, Uf uf){
        this.endereco = endereco;
        this.cidade = cidade;
        this.uf = uf;
    }

    // Getters e Setters
    public Endereco getEndereco(){return endereco;}
    public void setEndereco(Endereco endereco){this.endereco = endereco;}

    public Cidade getCidade(){return cidade;}
    public void setCidade(Cidade cidade){this.cidade = cidade;}

    public Uf getUf(){return uf;}
    public void setUf(Uf uf){this.uf = uf;}
}
