package br.com.alura.Desafio.Java.model;

public enum TipoVeiculos {
    carros,
    motos,
    caminhoes;

    public static boolean checkType(String tipo) {
        try {
            TipoVeiculos tipoVeiculos = TipoVeiculos.valueOf(tipo);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
