package com.ufcg.psoft.repositories;

import com.ufcg.psoft.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Categoria getCategoriaByNome(String nome);
}
