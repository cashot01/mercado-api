package br.com.fiap.mercado_api.controller;

import br.com.fiap.mercado_api.model.Personagem;
import br.com.fiap.mercado_api.service.PersonagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personagens")
public class PersonagemController {

    @Autowired
    private PersonagemService service;

    @GetMapping
    public Page<Personagem> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personagem> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Personagem> save(@RequestBody Personagem personagem) {
        return ResponseEntity.ok(service.save(personagem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Personagem> update(@PathVariable Long id, @RequestBody Personagem personagem) {
        personagem.setId(id);
        return ResponseEntity.ok(service.save(personagem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/nome")
    public Page<Personagem> findByNome(@RequestParam String nome, Pageable pageable) {
        return service.findByNome(nome, pageable);
    }

    @GetMapping("/search/classe")
    public Page<Personagem> findByClasse(@RequestParam String classe, Pageable pageable) {
        return service.findByClasse(classe, pageable);
    }
}
