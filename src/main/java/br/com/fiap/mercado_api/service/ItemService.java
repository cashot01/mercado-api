package br.com.fiap.mercado_api.service;

import br.com.fiap.mercado_api.model.Item;
import br.com.fiap.mercado_api.model.Raridade;
import br.com.fiap.mercado_api.model.TipoItem;
import br.com.fiap.mercado_api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public Page<Item> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Item findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new RuntimeException("Item não encontrado"));
    }

    public Item save(Item item) {
        return repository.save(item);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Item> findByNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Item> findByTipo(String tipo, Pageable pageable) {
        return repository.findByTipo(TipoItem.valueOf(tipo.toUpperCase()), pageable);
    }

    public Page<Item> findByRaridade(String raridade, Pageable pageable) {
        return repository.findByRaridade(Raridade.valueOf(raridade.toUpperCase()), pageable);
    }

    public Page<Item> findByPrecoBetween(double min, double max, Pageable pageable) {
        return repository.findByPrecoBetween(min, max, pageable);
    }

    public Page<Item> findItensSemDono(Pageable pageable) {
        return repository.findByDonoIsNull(pageable);
    }

    public Page<Item> findItensPorDono(Long donoId, Pageable pageable) {
        return repository.findByDonoId(donoId, pageable);
    }
}
