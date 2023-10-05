package br.com.alura.Desafio.Java.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFinais(@JsonAlias("Modelo") String modelo,
                          @JsonAlias("AnoModelo") Integer ano,
                          @JsonAlias("Valor") String valor,
                          @JsonAlias("Combustivel") String combustivel) {

    @Override
    public String toString() {
        return modelo()
                + "\t"
                + "ano: " + ano()
                + "\t"
                + "valor: " + valor()
                + "\t"
                + "combustivel: " + combustivel();
    }
}
