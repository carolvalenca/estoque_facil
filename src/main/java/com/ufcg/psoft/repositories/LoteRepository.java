package com.ufcg.psoft.repositories;

import com.ufcg.psoft.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {
	Lote findByProdutoId(Long id);
}
