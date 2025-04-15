package br.com.fiap.mercado_api.repository;

import br.com.fiap.mercado_api.model.Item;
import br.com.fiap.mercado_api.model.Raridade;
import br.com.fiap.mercado_api.model.TipoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Item> findByTipo(TipoItem tipo, Pageable pageable);
    Page<Item> findByRaridade(Raridade raridade, Pageable pageable);
    Page<Item> findByPrecoBetween(double precoMin, double precoMax, Pageable pageable);
    }
