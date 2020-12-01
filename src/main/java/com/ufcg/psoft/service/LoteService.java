package com.ufcg.psoft.service;

import java.util.Iterator;
import java.util.List;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;

import exceptions.ObjetoInexistenteException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteService {

	List<Lote> findAllLotes();

	List<Lote> findAllLotes(String[] sort);

	Lote findById(long id) throws ObjetoInexistenteException;

	void updateLote(Lote lote);

	void deleteLoteById(long id);

	int size();

	Iterator<Lote> getIterator();

	Lote saveLote(Lote lote);
	
	List<Produto> listaPertoVencer();

	List<Produto> listaVencidos();

	Lote findLoteByProdutoId(Long id);
	
	String relatorioLotes();

}
