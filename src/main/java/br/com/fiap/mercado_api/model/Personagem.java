package br.com.fiap.mercado_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Classe é obrigatória")
    private Classe classe;

    @Min(value = 1, message = "Nível mínimo é 1")
    @Max(value = 99, message = "Nível máximo é 99")
    private int nivel;

    @PositiveOrZero(message = "Moedas não podem ser negativas")
    private int moedas;

    // Getters e Setters (ou use Lombok)
}

