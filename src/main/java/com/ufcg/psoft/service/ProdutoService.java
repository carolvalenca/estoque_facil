package com.ufcg.psoft.service;

import java.util.Iterator;
import java.util.List;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.DTO.ProdutoClienteDTO;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.strategy.DescontoEnum;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoInvalidoException;
import exceptions.ObjetoJaExistenteException;

public interface ProdutoService {

	List<Produto> findAllProdutos();

	List<Produto> findAllProdutos(String[] sort);

	Produto saveProduto(Produto produto) throws ObjetoJaExistenteException, ObjetoInvalidoException;

	Produto findById(long id) throws ObjetoInexistenteException;

	void updateProduto(Produto user);

	Produto updateProdutoById(Long id, Produto produto) throws ObjetoInexistenteException;

	void deleteProdutoById(long id) throws ObjetoInexistenteException;

	int size();

	Iterator<Produto> getIterator();

	boolean doesProdutoExist(Produto produto);
	
	List<ProdutoClienteDTO> findProdutosIndisponiveis(int situacao);
	
	List<String> relatorioProdutos();

    Categoria mudaCategoria(String nomeCategoria, DescontoEnum tipoDesconto);

    List<Produto> getProdutosEmBaixa();
}
