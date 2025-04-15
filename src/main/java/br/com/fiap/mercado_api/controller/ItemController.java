package br.com.fiap.mercado_api.controller;

import br.com.fiap.mercado_api.model.Item;
import br.com.fiap.mercado_api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens")
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping
    public Page<Item> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Item> save(@RequestBody Item item) {
        return ResponseEntity.ok(service.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        return ResponseEntity.ok(service.save(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/nome")
    public Page<Item> findByNome(@RequestParam String nome, Pageable pageable) {
        return service.findByNome(nome, pageable);
    }

    @GetMapping("/search/tipo")
    public Page<Item> findByTipo(@RequestParam String tipo, Pageable pageable) {
        return service.findByTipo(tipo, pageable);
    }

    @GetMapping("/search/raridade")
    public Page<Item> findByRaridade(@RequestParam String raridade, Pageable pageable) {
        return service.findByRaridade(raridade, pageable);
    }

    @GetMapping("/search/preco")
    public Page<Item> findByPrecoBetween(
            @RequestParam double min,
            @RequestParam double max,
            Pageable pageable) {
        return service.findByPrecoBetween(min, max, pageable);
    }
}
