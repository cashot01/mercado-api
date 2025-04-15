package br.com.fiap.mercado_api.config;

import br.com.fiap.mercado_api.models.*;
import br.com.fiap.mercado_api.repositories.ItemRepository;
import br.com.fiap.mercado_api.repositories.PersonagemRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder {

    @Autowired
    private PersonagemRepository personagemRepository;

    @Autowired
    private ItemRepository itemRepository;

    private final Random random = new Random();

    @PostConstruct
    public void seed() {
        var personagens = List.of(
                Personagem.builder().nome("Arthas").classe(EPersonagemClass.GUERREIRO).nivel(10).moedas(BigDecimal.valueOf(150)).build(),
                Personagem.builder().nome("Merlin").classe(EPersonagemClass.MAGO).nivel(12).moedas(BigDecimal.valueOf(200)).build(),
                Personagem.builder().nome("Luna").classe(EPersonagemClass.ARQUEIRO).nivel(8).moedas(BigDecimal.valueOf(95)).build()
        );

        personagemRepository.saveAll(personagens);

        var nomes = List.of("Espada Longa", "Cajado de Fogo", "Arco Élfico", "Poção de Cura", "Escudo de Ferro", "Adaga Sombria");

        var itens = new ArrayList<Item>();

        for (int i = 0; i < 20; i++) {
            itens.add(
                    Item.builder()
                            .nome(nomes.get(random.nextInt(nomes.size())))
                            .type(randomEnum(EItemType.class))
                            .rarity(randomEnum(EItemRarity.class))
                            .price(BigDecimal.valueOf(10 + random.nextDouble() * 200))
                            .owner(personagens.get(random.nextInt(personagens.size())))
                            .build()
            );
        }

        itemRepository.saveAll(itens);
    }

    private <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        var constants = clazz.getEnumConstants();
        return constants[random.nextInt(constants.length)];
    }
}
