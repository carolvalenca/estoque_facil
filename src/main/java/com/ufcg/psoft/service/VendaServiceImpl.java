package com.ufcg.psoft.service;

import java.util.List;

import com.ufcg.psoft.util.OrderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import exceptions.ObjetoInexistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.ProdutoVenda;
import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.repositories.VendaRepository;

@Service("vendaService")
public class VendaServiceImpl implements VendaService {

	@Autowired
	private VendaRepository vendaRepository;
	
	@Override
	public Venda saveVenda(Venda venda) {
		Venda savedVenda = this.vendaRepository.save(venda);
		
		return savedVenda;
	}

	@Override
	public List<Venda> listAllVendas() {
		return vendaRepository.findAll();
	}

	@Override
	public Venda findById(long id) throws ObjetoInexistenteException {
		Venda venda = vendaRepository.getVendaById(id);

		if (venda == null) {
			throw new ObjetoInexistenteException("Venda nao existe");
		}
		return venda;
	}

	@Override
	public void deleteVendaById(long id) {
		this.vendaRepository.deleteById(id);
  }

	public List<Venda> listAllVenda(String[] sort) {
		List<Order> sortingOrders = OrderHelper.getSortingOrders(sort);
		return this.vendaRepository.findAll(Sort.by(sortingOrders));

	}

	public List<String> relatorioVenda() {
		// TODO Auto-generated method stub
		List<Venda> vendasRealizadas = this.listAllVendas();
		BigDecimal total = new BigDecimal(0);
		List<String> vendas = new ArrayList<String>();
		for (Venda venda : vendasRealizadas) {
			for (ProdutoVenda produtoVenda : venda.getProdutos()) {
				vendas.add(produtoVenda.toString());
			}
			total = total.add(venda.getValorTotal());
		}
		String totalVendas =  "Receita total: R$ " + total.toString();
		vendas.add(totalVendas);
		return vendas;
	}

}
