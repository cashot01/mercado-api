package br.com.fiap.mercado_api.controllers;

import br.com.fiap.mercado_api.models.EPersonagemClass;
import br.com.fiap.mercado_api.models.Personagem;
import br.com.fiap.mercado_api.repositories.PersonagemRepository;
import br.com.fiap.mercado_api.specification.PersonagemSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/personagem")
@Slf4j
public class PersonagemController {

    public record PersonagemFilter(String nome, EPersonagemClass classe){}

    @Autowired
    private PersonagemRepository personagemRepository;

    @GetMapping
    @Cacheable("personagem")
    @Operation(description = "Get all personagens",
        tags = "personagem", summary = "Personagem's list")
    public Page<Personagem> index(PersonagemFilter filter, @PageableDefault(size = 20) Pageable pageable){
        Specification<Personagem> specification = PersonagemSpecification.withFilters(filter);
        return personagemRepository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "personagem", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<Personagem> createPersonagem(@RequestBody @Valid Personagem personagem){
        personagemRepository.save(personagem);
        return ResponseEntity.status(201).body(personagem);
    }

    @GetMapping("{id}")
    public Personagem getById(@PathVariable Long id){
        return getPersonagem(id);
    }

    @PutMapping("{id}")
    public Personagem updatePersonagem(@PathVariable Long id, @RequestBody @Valid Personagem updatedPersonagem) {
        Personagem existingPersonagem = getPersonagem(id);

        existingPersonagem.setNome(updatedPersonagem.getNome());
        existingPersonagem.setClasse(updatedPersonagem.getClasse());
        existingPersonagem.setNivel(updatedPersonagem.getNivel());
        existingPersonagem.setMoedas(updatedPersonagem.getMoedas());

        return personagemRepository.save(existingPersonagem);
    }

    @DeleteMapping("{id}")
    public void deletePersonagem(@PathVariable Long id) {
        personagemRepository.delete(getPersonagem(id));
    }

    private Personagem getPersonagem(Long id){
        return personagemRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jogo não encontrado")
                );
    }
}