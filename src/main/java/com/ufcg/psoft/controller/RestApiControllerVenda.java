package com.ufcg.psoft.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.ProdutoVenda;
import com.ufcg.psoft.model.Venda;
import com.ufcg.psoft.model.DTO.ProdutoVendaDTO;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.LoteServiceImpl;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.ProdutoServiceImpl;
import com.ufcg.psoft.service.VendaService;
import com.ufcg.psoft.service.VendaServiceImpl;
import com.ufcg.psoft.util.CustomErrorType;

import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoInvalidoException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiControllerVenda {

	@Autowired
	ProdutoService produtoService = new ProdutoServiceImpl();

	@Autowired
	LoteService loteService = new LoteServiceImpl();
	
	@Autowired
	VendaService vendaService = new VendaServiceImpl();
	
	@RequestMapping(value = "/venda/", method = RequestMethod.POST)
	public ResponseEntity<?> registrarVenda(@RequestBody ArrayList<ProdutoVendaDTO> produtos) throws ObjetoInexistenteException, ObjetoInvalidoException{
		List<ProdutoVenda> produtosVenda = new ArrayList<ProdutoVenda>();
		
		float valorTotal = 0;
		
		for (ProdutoVendaDTO produtoVenda : produtos) {
			Produto produto = produtoService.findById(produtoVenda.getIdProduto());
			
			if (produto.getSituacao() == Produto.INDISPONIVEL) {
				return new ResponseEntity<>(new CustomErrorType("Error: Produto" + produto.getNome() + " do fabricante "
						+ produto.getFabricante() + " não está disponível!"), HttpStatus.NOT_ACCEPTABLE);
			}
			
			Lote lote = loteService.findLoteByProdutoId(produto.getId());
			
			if (lote.getNumeroDeItens() < produtoVenda.getQtdItens()) {
				return new ResponseEntity<>(new CustomErrorType("Error: Lote do produto "
						+ produto.getNome() + " não está disponível!"), HttpStatus.NOT_ACCEPTABLE);
			}
			
			this.subQtdItensLote(lote, produtoVenda.getQtdItens());
			
			BigDecimal valor = this.getPrecoTotalProduto(produto, produtoVenda.getQtdItens());

			valorTotal += valor.floatValue();
			
			produtosVenda.add(new ProdutoVenda(produto, produtoVenda.getQtdItens(), valor));
		}

		Venda venda = vendaService.saveVenda(new Venda(produtosVenda, new BigDecimal(valorTotal)));
		
		return new ResponseEntity<>(venda, HttpStatus.CREATED);
	}
	
	private void subQtdItensLote(Lote lote, int qtdItens) throws ObjetoInvalidoException {
		lote.setNumeroDeItens(lote.getNumeroDeItens() - qtdItens);
		
		if (lote.getNumeroDeItens() == 0) {
			this.setSituacaoProduto(lote.getProduto(), 2);
		}
		
		loteService.saveLote(lote);
	}
	
	private void addQtdItensLote(Lote lote, int qtdItens) throws ObjetoInvalidoException {
		lote.setNumeroDeItens(lote.getNumeroDeItens() + qtdItens);

		if (lote.getNumeroDeItens() > 0) {
			this.setSituacaoProduto(lote.getProduto(), 1);
		}
		
		loteService.saveLote(lote);
	}
	
	private void setSituacaoProduto(Produto produto, int situacao) throws ObjetoInvalidoException {
		produto.mudaSituacao(situacao);
		produtoService.updateProduto(produto);
	}
	
	private BigDecimal getPrecoTotalProduto(Produto produto, int qtdItens) {
		BigDecimal valorProduto = produto.getPrecoComDesconto();
		
		BigDecimal valorTotal = valorProduto.multiply(new BigDecimal(qtdItens));
		
		return valorTotal;
	}

	@RequestMapping(value = "/venda/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarVenda(@PathVariable("id") long id) throws ObjetoInexistenteException, ObjetoInvalidoException {
		Venda venda = vendaService.findById(id);

		if (venda == null) {
			return new ResponseEntity(new CustomErrorType("Unable to delete. Venda with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		List<ProdutoVenda> produtos = venda.getProdutos();

		for (ProdutoVenda p : produtos) {
			Produto produto = p.getProduto();
			Lote lote = loteService.findLoteByProdutoId(produto.getId());

			if (produto == null || lote == null) {
				return new ResponseEntity(new CustomErrorType("Item not found."),
						HttpStatus.NOT_FOUND);
			}
//			lote.setNumeroDeItens(lote.getNumeroDeItens() + p.getQtdItens());
//
//			if (lote.getNumeroDeItens() > 0) {
//				produto.mudaSituacao(1);
//			}
			
			this.addQtdItensLote(lote, p.getQtdItens());
		}

		Map<String, String> msg = new HashMap<>();
		msg.put("Msg", "Venda com id " + id + " apagada");
		vendaService.deleteVendaById(id);
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/venda/", method = RequestMethod.GET)
	public ResponseEntity<List<Venda>> listarVendas(@RequestParam(defaultValue = "id,asc") String[] sort){
		List<Venda> vendas = this.vendaService.listAllVenda(sort);
		if(vendas.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(vendas, HttpStatus.OK);
	}
}

