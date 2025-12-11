package com.startdb.sust;

public enum DiaSemana {
    SEGUNDA("Segunda", "Monday"),
    TERÇA("Terça", "Tuesday"),
    QUARTA("Quarta", "Wednesday"),
    QUINTA("Quinta", "Thursday"),
    SEXTA("Sexta", "Friday"),
    SÁBADO("Sábado", "Saturday"),
    DOMINGO("Domingo", "Sunday");

    private final String nomePortugues;
    private final String nomeIngles;

    DiaSemana(String nomePortugues, String nomeIngles) {
        this.nomePortugues = nomePortugues;
        this.nomeIngles = nomeIngles;
    }

    public String getNomePortugues() {
        return nomePortugues;
    }

    public String getNomeIngles() {
        return nomeIngles;
    }

    // Método auxiliar para buscar por nome português
    public static DiaSemana porNomePortugues(String nome) {
        for (DiaSemana dia : DiaSemana.values()) {
            if (dia.nomePortugues.equalsIgnoreCase(nome)) {
                return dia;
            }
        }
        throw new IllegalArgumentException("Dia da semana inválido: " + nome);
    }

    // Obter dia da semana atual
    public static DiaSemana getHoje() {
        java.time.DayOfWeek hoje = java.time.LocalDate.now().getDayOfWeek();
        return switch (hoje) {
            case MONDAY -> SEGUNDA;
            case TUESDAY -> TERÇA;
            case WEDNESDAY -> QUARTA;
            case THURSDAY -> QUINTA;
            case FRIDAY -> SEXTA;
            case SATURDAY -> SÁBADO;
            case SUNDAY -> DOMINGO;
        };
    }
}
