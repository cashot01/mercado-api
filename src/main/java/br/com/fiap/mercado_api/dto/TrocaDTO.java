package br.com.fiap.mercado_api.dto;

import java.util.List;

public class TrocaDTO {
    private Long personagem1Id;
    private Long personagem2Id;
    private List<Long> itensPersonagem1;
    private List<Long> itensPersonagem2;

    public Long getPersonagem1Id() {
        return personagem1Id;
    }

    public void setPersonagem1Id(Long personagem1Id) {
        this.personagem1Id = personagem1Id;
    }

    public Long getPersonagem2Id() {
        return personagem2Id;
    }

    public void setPersonagem2Id(Long personagem2Id) {
        this.personagem2Id = personagem2Id;
    }

    public List<Long> getItensPersonagem1() {
        return itensPersonagem1;
    }

    public void setItensPersonagem1(List<Long> itensPersonagem1) {
        this.itensPersonagem1 = itensPersonagem1;
    }

    public List<Long> getItensPersonagem2() {
        return itensPersonagem2;
    }

    public void setItensPersonagem2(List<Long> itensPersonagem2) {
        this.itensPersonagem2 = itensPersonagem2;
    }
}
