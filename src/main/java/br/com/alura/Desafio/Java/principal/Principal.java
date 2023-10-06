package br.com.alura.Desafio.Java.principal;

import br.com.alura.Desafio.Java.model.DadosFinais;
import br.com.alura.Desafio.Java.model.DadosIniciais;
import br.com.alura.Desafio.Java.model.DadosModelo;
import br.com.alura.Desafio.Java.model.TipoVeiculos;
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
        exibeTitulo();

        System.out.println("Tipos de veículos:\n Carros, Motos e Caminhoes");

        System.out.println("\nDigite o tipo de veículo: ");
        String tipoDeVeiculo = leitura.nextLine();
        System.out.println("\nExibindo as marcas:");
        exibeTodosVeiculos(obterListaTodosVeiculos(tipoDeVeiculo));

        System.out.println("\nDigite o código da marca: ");
        String codigoMarca = leitura.nextLine();
        System.out.println("\nExibindo os modelos da marca:");
        exibeModelosDaMarca(tipoDeVeiculo, codigoMarca);

        System.out.println("\nDigite o nome do modelo: ");
        String nomeModelo = leitura.nextLine();
        System.out.println("\nExibindo os veículos do modelo:");
        exibeVeiculosDoModelo(obterModelosDaMarca(tipoDeVeiculo, codigoMarca), nomeModelo);

        System.out.println("\nDigite o código do modelo: ");
        String codigoModelo = leitura.nextLine();
        System.out.println("\nTodos os veículos filtrados com avaliações por ano: ");
        exibeDadosFinais(tipoDeVeiculo, codigoMarca, codigoModelo);
    }

    public void exibeTitulo() {
        System.out.println(
                "\t\t\t\t\t\t\t\t \uD835\uDD3B\uD835\uDD56\uD835\uDD64\uD835\uDD52\uD835\uDD57\uD835\uDD5A\uD835\uDD60 \uD835\uDD41\uD835\uDD52\uD835\uDD67\uD835\uDD52" +
                "\n" +
                "████████╗░█████╗░██████╗░███████╗██╗░░░░░░█████╗░  ███████╗██╗██████╗░███████╗\n" +
                "╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██║░░░░░██╔══██╗  ██╔════╝██║██╔══██╗██╔════╝\n" +
                "░░░██║░░░███████║██████╦╝█████╗░░██║░░░░░███████║  █████╗░░██║██████╔╝█████╗░░\n" +
                "░░░██║░░░██╔══██║██╔══██╗██╔══╝░░██║░░░░░██╔══██║  ██╔══╝░░██║██╔═══╝░██╔══╝░░\n" +
                "░░░██║░░░██║░░██║██████╦╝███████╗███████╗██║░░██║  ██║░░░░░██║██║░░░░░███████╗\n" +
                "░░░╚═╝░░░╚═╝░░╚═╝╚═════╝░╚══════╝╚══════╝╚═╝░░╚═╝  ╚═╝░░░░░╚═╝╚═╝░░░░░╚══════╝\n"
        );
    }

    public List<DadosIniciais> obterListaTodosVeiculos(String tipoDeVeiculo) {
        tipoDeVeiculo = tipoDeVeiculo.toLowerCase();

        if (!TipoVeiculos.checkType(tipoDeVeiculo)) {
            throw new IllegalArgumentException("Tipo de veículo inválido!");
        }

        var jsonMarcas = consumo.obterDados(ENDERECO + tipoDeVeiculo + "/marcas");

        List<DadosIniciais> listDadosIniciais = conversor.obterDados(jsonMarcas, DadosIniciais.class);
        return listDadosIniciais;
    }

    private void exibeTodosVeiculos(List<DadosIniciais> listaTodosVeiculos) {
        listaTodosVeiculos.forEach(System.out::println);
    }

    private void exibeModelosDaMarca(String tipoDeVeiculo, String codigoMarca) {
        var result = obterModelosDaMarca(tipoDeVeiculo, codigoMarca);
        result.modelos().forEach(System.out::println);
    }

    private DadosModelo obterModelosDaMarca(String tipoDeVeiculo, String codigoMarca) {
        var jsonModelos = consumo.obterDados(ENDERECO
                + tipoDeVeiculo.toLowerCase()
                + "/marcas/"
                + codigoMarca
                + "/modelos"
        );
        DadosModelo dadosModelo = conversor.map(jsonModelos, DadosModelo.class);
        return dadosModelo;
    }

    private void exibeVeiculosDoModelo(DadosModelo dadosModelo, String nomeModelo) {
        var listaDeModelos = validarEObterModelos(dadosModelo, nomeModelo);
        listaDeModelos.forEach(System.out::println);
    }

    public List<DadosIniciais> validarEObterModelos(DadosModelo dadosModelo, String nomeModelo) {

        List<DadosIniciais> listaDeModelos = dadosModelo.modelos().stream()
                .filter(n -> n.nome()
                        .contains(nomeModelo.substring(0, 1).toUpperCase()
                                + nomeModelo.substring(1).toLowerCase()))
                .collect(Collectors.toList());

        if (listaDeModelos.isEmpty()) {
            listaDeModelos = dadosModelo.modelos().stream()
                    .filter(n -> n.nome().contains(nomeModelo.toUpperCase()))
                    .collect(Collectors.toList());
        }

        if (listaDeModelos.isEmpty()) {
            listaDeModelos = dadosModelo.modelos().stream()
                    .filter(n -> n.nome().contains(nomeModelo))
                    .collect(Collectors.toList());
        }

        if (listaDeModelos.isEmpty()) {
            throw new IllegalArgumentException("Nome do modelo inválido!");
        }

        return listaDeModelos;
    }

    private void exibeDadosFinais(String tipoDeVeiculo, String codigoMarca, String codigoModelo) {
        var listAnos = obterAnos(tipoDeVeiculo, codigoMarca, codigoModelo);

        listAnos.forEach(v -> {
            var dadosFinais = obterDadosVeiculo(tipoDeVeiculo, codigoMarca, codigoModelo, v.codigo());
            System.out.println(dadosFinais.toString());
        });
    }

    public List<DadosIniciais> obterAnos(String tipoDeVeiculo, String codigoMarca, String codigoModelo) {
        var jsonAnos = consumo.obterDados(ENDERECO
                + tipoDeVeiculo.toLowerCase()
                + "/marcas/"
                + codigoMarca
                + "/modelos/"
                + codigoModelo
                + "/anos"
        );
        List<DadosIniciais> listAnos = conversor.obterDados(jsonAnos, DadosIniciais.class);
        return listAnos;
    }

    public DadosFinais obterDadosVeiculo(String tipoDeVeiculo, String codigoMarca, String codigoModelo, String ano) {
        var jsonDadosVeiculo = consumo.obterDados(ENDERECO
                + tipoDeVeiculo.toLowerCase()
                + "/marcas/"
                + codigoMarca
                + "/modelos/"
                + codigoModelo
                + "/anos/"
                + ano
        );

        var dadosFinais = conversor.map(jsonDadosVeiculo, DadosFinais.class);
        return dadosFinais;
    }
}
