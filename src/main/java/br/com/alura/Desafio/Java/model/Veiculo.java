package br.com.alura.Desafio.Java.model;

public class Veiculo {

    private String nome;
    private String ano;
    private Integer valor;
    private String combustivel;

    public Veiculo(String nome, String ano, Integer valor, String combustivel) {
        this.nome = nome;
        this.ano = ano;
        this.valor = valor;
        this.combustivel = combustivel;
    }

    public String getNome() {
        return nome;
    }

    public String getAno() {
        return ano;
    }

    public Integer getValor() {
        return valor;
    }

    public String getCombustivel() {
        return combustivel;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "nome='" + nome + '\'' +
                ", ano='" + ano + '\'' +
                ", valor=" + valor +
                ", combustivel='" + combustivel + '\'' +
                '}';
    }
}
