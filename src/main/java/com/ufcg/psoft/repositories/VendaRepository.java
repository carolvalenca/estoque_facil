package com.ufcg.psoft.repositories;

import com.ufcg.psoft.model.ProdutoVenda;
import com.ufcg.psoft.model.Venda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    public Venda getVendaById(Long id);
}

