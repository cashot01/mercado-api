package br.com.fiap.mercado_api.repository;

import br.com.fiap.mercado_api.model.Classe;
import br.com.fiap.mercado_api.model.Personagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
    Page<Personagem> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Personagem> findByClasse(Classe classe, Pageable pageable);
}
