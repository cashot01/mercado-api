package br.com.fiap.mercado_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo é obrigatório")
    private TipoItem tipo;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Raridade é obrigatória")
    private Raridade raridade;

    @Positive(message = "Preço deve ser positivo")
    private double preco;

    @ManyToOne
    @JoinColumn(name = "personagem_id")
    private Personagem dono;


}



