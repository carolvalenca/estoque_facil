package com.ufcg.psoft.model.DTO;

import java.time.LocalDate;

public class LoteDTO {

    private int numeroDeItens;
    private LocalDate dataDeValidade;

    public LoteDTO() {
    }

    public LoteDTO(int numeroDeItens, LocalDate dataDeValidade) {
        super();
        this.numeroDeItens = numeroDeItens;
        this.dataDeValidade = dataDeValidade;
    }

    public int getNumeroDeItens() {
        return numeroDeItens;
    }

    public void setNumeroDeItens(int numeroDeItens) {
        this.numeroDeItens = numeroDeItens;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
}
