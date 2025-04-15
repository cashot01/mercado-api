package br.com.fiap.mercado_api.service;

import br.com.fiap.mercado_api.dto.CompraDTO;
import br.com.fiap.mercado_api.dto.TrocaDTO;
import br.com.fiap.mercado_api.dto.VendaDTO;
import br.com.fiap.mercado_api.model.Item;
import br.com.fiap.mercado_api.model.Personagem;
import br.com.fiap.mercado_api.repository.ItemRepository;
import br.com.fiap.mercado_api.repository.PersonagemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MercadoService {

    @Autowired
    private PersonagemRepository personagemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Transactional
    public Item comprarItem(CompraDTO compraDTO) {
        Personagem personagem = personagemRepository.findById(compraDTO.getPersonagemId())
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));

        Item item = itemRepository.findById(compraDTO.getItemId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (item.getDono() != null) {
            throw new RuntimeException("Item já possui dono");
        }

        if (personagem.getMoedas() < item.getPreco()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        personagem.setMoedas(personagem.getMoedas() - item.getPreco());
        item.setDono(personagem);

        personagemRepository.save(personagem);
        return itemRepository.save(item);
    }

    @Transactional
    public Item venderItem(VendaDTO vendaDTO) {
        Personagem personagem = personagemRepository.findById(vendaDTO.getPersonagemId())
                .orElseThrow(() -> new RuntimeException("Personagem não encontrado"));

        Item item = itemRepository.findById(vendaDTO.getItemId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (!item.getDono().getId().equals(personagem.getId())) {
            throw new RuntimeException("Item não pertence ao personagem");
        }

        item.setDono(null);
        item.setPreco(vendaDTO.getPreco());

        return itemRepository.save(item);
    }

    @Transactional
    public String trocarItens(TrocaDTO trocaDTO) {
        Personagem personagem1 = personagemRepository.findById(trocaDTO.getPersonagem1Id())
                .orElseThrow(() -> new RuntimeException("Personagem 1 não encontrado"));

        Personagem personagem2 = personagemRepository.findById(trocaDTO.getPersonagem2Id())
                .orElseThrow(() -> new RuntimeException("Personagem 2 não encontrado"));

        List<Item> itensP1 = itemRepository.findAllById(trocaDTO.getItensPersonagem1());
        List<Item> itensP2 = itemRepository.findAllById(trocaDTO.getItensPersonagem2());

        // Verificar se todos os itens pertencem aos personagens corretos
        itensP1.forEach(item -> {
            if (!item.getDono().getId().equals(personagem1.getId())) {
                throw new RuntimeException("Item " + item.getId() + " não pertence ao personagem 1");
            }
        });

        itensP2.forEach(item -> {
            if (!item.getDono().getId().equals(personagem2.getId())) {
                throw new RuntimeException("Item " + item.getId() + " não pertence ao personagem 2");
            }
        });


        itensP1.forEach(item -> item.setDono(personagem2));
        itensP2.forEach(item -> item.setDono(personagem1));

        itemRepository.saveAll(itensP1);
        itemRepository.saveAll(itensP2);

        return "Troca realizada com sucesso";
    }
}
