package br.com.fiap.mercado_api.service;

import br.com.fiap.mercado_api.model.Classe;
import br.com.fiap.mercado_api.model.Item;
import br.com.fiap.mercado_api.model.Personagem;
import br.com.fiap.mercado_api.repository.PersonagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PersonagemService {

    @Autowired
    private PersonagemRepository repository;

    public Page<Personagem> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Personagem findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new RuntimeException("Personagem não encontrado"));
    }

    public Personagem save(Personagem personagem) {
        return repository.save(personagem);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Personagem> findByNome(String nome, Pageable pageable) {
        return repository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Personagem> findByClasse(String classe, Pageable pageable) {
        return repository.findByClasse(Classe.valueOf(classe.toUpperCase()), pageable);
    }

    public Page<Item> getInventario(Long personagemId, Pageable pageable) {
        return itemRepository.findByDonoId(personagemId, pageable);
    }

    public Personagem adicionarMoedas(Long personagemId, int quantidade) {
        Personagem personagem = findById(personagemId);
        personagem.setMoedas(personagem.getMoedas() + quantidade);
        return repository.save(personagem);
    }
}
