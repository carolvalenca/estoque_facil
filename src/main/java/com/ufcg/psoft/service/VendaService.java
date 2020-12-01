package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.model.Venda;
import exceptions.ObjetoInexistenteException;

public interface VendaService {

	Venda saveVenda(Venda venda);
	
	List<Venda> listAllVendas();

	List<Venda> listAllVenda(String[] sort);
	
	List<String> relatorioVenda();

  Venda findById(long id) throws ObjetoInexistenteException;

	void deleteVendaById(long id);
}
