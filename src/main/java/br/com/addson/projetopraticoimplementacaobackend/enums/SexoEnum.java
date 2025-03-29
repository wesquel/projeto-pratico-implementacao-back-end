package br.com.addson.projetopraticoimplementacaobackend.enums;

public enum SexoEnum {
    MASCULINO("masculino"),
    FEMININO("feminino");

    private final String valor;

    SexoEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static SexoEnum fromInput(String input) {
        switch (input.toUpperCase()) {
            case "M":
                return MASCULINO;
            case "MASCULINO":
                return MASCULINO;
            case "F":
                return FEMININO;
            case "FEMININO":
                return FEMININO;
            default:
                throw new IllegalArgumentException(
                        "Sexo inválido: " + input + ". Use 'M', 'MASCULINO', 'F' ou 'FEMININO'.");
        }
    }
}
