package br.com.alura.Desafio.Java.principal;

import br.com.alura.Desafio.Java.model.DadosFinais;
import br.com.alura.Desafio.Java.model.DadosIniciais;
import br.com.alura.Desafio.Java.model.DadosModelo;
import br.com.alura.Desafio.Java.service.ConsumoAPI;
import br.com.alura.Desafio.Java.service.ConverteDados;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    private Scanner leitura = new Scanner(System.in);
    ConsumoAPI consumo = new ConsumoAPI();
    ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        System.out.println("Digite o tipo de veículo: ");
        String tipoDeVeiculo = leitura.nextLine();

        exibeTodosVeiculos(tipoDeVeiculo);

        System.out.println("\nDigite o código da marca: ");
        String codigoMarca = leitura.nextLine();

        var dadosModelo = exibeModelosDaMarca(tipoDeVeiculo, codigoMarca);

        System.out.println("\nDigite o nome do modelo: ");
        String nomeModelo = leitura.nextLine();

        exibeCarrosDoModelo(dadosModelo, nomeModelo);

        System.out.println("\nDigite o código do modelo: ");
        String codigoModelo = leitura.nextLine();

        exibeDadosFinais(tipoDeVeiculo, codigoMarca, codigoModelo);

    }

    private String exibeTodosVeiculos(String tipoDeVeiculo) {
        var jsonMarcas = consumo.obterDados(ENDERECO + tipoDeVeiculo.toLowerCase() + "/marcas");

        List<DadosIniciais> listDadosIniciais = conversor.obterDados(jsonMarcas, DadosIniciais.class);
//        listDadosIniciais.forEach(d -> System.out.println("Código: " + d.codigo() + "\t" + "Nome: " + d.nome()));
        listDadosIniciais.forEach(System.out::println);

        return tipoDeVeiculo;
    }

    private DadosModelo exibeModelosDaMarca(String tipoDeVeiculo, String codigoMarca) {
        var jsonModelos = consumo.obterDados(ENDERECO
                + tipoDeVeiculo.toLowerCase()
                + "/marcas/"
                + codigoMarca
                + "/modelos"
        );

        DadosModelo result = conversor.map(jsonModelos, DadosModelo.class);
        result.modelos().forEach(System.out::println);
        return result;
    }

    private void exibeCarrosDoModelo(DadosModelo result, String nomeModelo) {
        List<DadosIniciais> resultModelos = result.modelos().stream()
                .filter(n -> n.nome()
                        .contains(nomeModelo.substring(0, 1).toUpperCase()
                                + nomeModelo.substring(1).toLowerCase()))
                .collect(Collectors.toList());

        if (resultModelos.isEmpty()) {
            resultModelos = result.modelos().stream()
                    .filter(n -> n.nome().contains(nomeModelo))
                    .collect(Collectors.toList());
        }

        resultModelos.forEach(System.out::println);
    }

    private void exibeDadosFinais(String tipoDeVeiculo, String codigoMarca, String codigoModelo) {
        var jsonAnoModelos = consumo.obterDados(ENDERECO
                + tipoDeVeiculo.toLowerCase()
                + "/marcas/"
                + codigoMarca
                + "/modelos/"
                + codigoModelo
                + "/anos"
        );

        List<DadosIniciais> listModelosEAnos = conversor.obterDados(jsonAnoModelos, DadosIniciais.class);

        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");

        listModelosEAnos.forEach(v -> {
            var jsonVeiculoEscolhido = consumo.obterDados(ENDERECO
                    + tipoDeVeiculo.toLowerCase()
                    + "/marcas/"
                    + codigoMarca
                    + "/modelos/"
                    + codigoModelo
                    + "/anos/"
                    + v.codigo()
            );

            var dadosFinais = conversor.map(jsonVeiculoEscolhido, DadosFinais.class);
            System.out.println(dadosFinais.toString());
        });
    }
}
