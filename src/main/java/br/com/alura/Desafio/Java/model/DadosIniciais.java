package br.com.alura.Desafio.Java.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosIniciais(@JsonAlias("codigo") String codigo,
                            @JsonAlias("nome") String nome){

    @Override
    public String toString() {
        return "Código: " + codigo() + "\t" + "Nome: " + nome();
    }
}
