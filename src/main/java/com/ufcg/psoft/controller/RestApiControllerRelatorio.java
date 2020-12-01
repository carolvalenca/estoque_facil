package com.ufcg.psoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.DTO.LoteDTO;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.LoteServiceImpl;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.ProdutoServiceImpl;
import com.ufcg.psoft.service.VendaService;
import com.ufcg.psoft.service.VendaServiceImpl;

import exceptions.ObjetoInexistenteException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiControllerRelatorio {

	@Autowired
	ProdutoService produtoService = new ProdutoServiceImpl();

	@Autowired
	LoteService loteService = new LoteServiceImpl();	
	
	@Autowired
	VendaService vendaService = new VendaServiceImpl();
	
	private static final String QUEBRA_LINHA = "\n";
	
	@RequestMapping(value = "/relatorio/", method = RequestMethod.GET)
	public ResponseEntity<Object> gerarRelatorio() throws ObjetoInexistenteException {
		List<Produto> produto = produtoService.findAllProdutos();
		
		List<String> produtos = produtoService.relatorioProdutos();
		
		List<Lote> lotes = loteService.findAllLotes();
		
		String relatorio = "---- Relatório Produtos ----" + QUEBRA_LINHA + "Possuimos os seguintes produtos:" + QUEBRA_LINHA;
		
		int zero = 0;
		int totalProdutos = zero;
		
		for (Produto prod : produto) {
			
			for (Lote lote : lotes) {
				if (prod.equals(lote.getProduto())) {
					relatorio += prod.toString();
					relatorio += " com cerca de " + lote.getNumeroDeItens() + " unidades." + QUEBRA_LINHA;
					totalProdutos += lote.getNumeroDeItens();
				}
			}
		}
		relatorio += "Total de produtos:" + totalProdutos +  "." + QUEBRA_LINHA + QUEBRA_LINHA;
		
		String lotesRelatorio = loteService.relatorioLotes();
		relatorio += "---- Relatório Lotes ----" + QUEBRA_LINHA;
		relatorio += lotesRelatorio + QUEBRA_LINHA;
		
		List<String> vendas = vendaService.relatorioVenda();
		
		Object[] vendaString = vendas.toArray();
		
		relatorio += "---- Relatório Vendas ----" + QUEBRA_LINHA;
		for (int i = zero; i < vendaString.length; i++) {
			relatorio += vendaString[i];
			relatorio += QUEBRA_LINHA;
		}
		return new ResponseEntity<Object>(relatorio, HttpStatus.OK);
	}
	
}
