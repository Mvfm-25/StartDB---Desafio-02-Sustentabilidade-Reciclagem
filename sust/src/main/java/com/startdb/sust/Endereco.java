// Classe para representação de Endereços dos pontos de coleta.
// Acredito que isso vá a ajudar a centralização de informação.
// [mvfm]
//
// Criado : 24/11/2025  || Última modificação : 24/11/2025

package com.startdb.sust;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String complemento;

    public Endereco(){}

    public Endereco(String rua, String numero, String bairro, String cidade, String uf, String cep, String complemento){
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.complemento = complemento;
    }

    // Getters e Setters
    public String getRua(){return rua;}
    public void setRua(String rua){this.rua = rua;}

    public String getNumero(){return numero;}
    public void setNumero(String numero){this.numero = numero;}

    public String getBairro(){return bairro;}
    public void setBairro(String bairro){this.bairro = bairro;}

    public String getCidade(){return cidade;}
    public void setCidade(String cidade){this.cidade = cidade;}

    public String getUf(){return uf;}
    public void setUf(String uf){this.uf = uf;}

    public String getCep(){return cep;}
    public void setCep(String cep){this.cep = cep;}

    public String getComplemento(){return complemento;}
    public void setComplemento(String complemento){this.complemento = complemento;}

    // Método auxiliar para retornar endereço formatado
    public String getEnderecoCompleto(){
        return rua + ", " + numero + " - " + bairro + " - " + cidade + " - " + uf + " - " + cep + 
               (complemento != null && !complemento.isEmpty() ? " (" + complemento + ")" : "");
    }
}
