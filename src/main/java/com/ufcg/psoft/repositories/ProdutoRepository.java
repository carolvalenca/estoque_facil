package com.ufcg.psoft.repositories;

import com.ufcg.psoft.model.Produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    public Produto getProdutoById(Long id);
    
    public List<Produto> findBySituacao(int situacao);

    @Query("SELECT p FROM Produto p WHERE p.lote.numeroDeItens < 15")
    List<Produto> findProdutosEmBaixa();
}