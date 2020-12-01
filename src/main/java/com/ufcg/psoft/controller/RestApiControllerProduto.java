package com.ufcg.psoft.controller;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.model.DTO.ProdutoClienteDTO;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.LoteServiceImpl;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.ProdutoServiceImpl;
import com.ufcg.psoft.util.CustomErrorType;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoInvalidoException;
import exceptions.ObjetoJaExistenteException;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiControllerProduto {

	@Autowired
	ProdutoService produtoService = new ProdutoServiceImpl();

	@Autowired
	LoteService loteService = new LoteServiceImpl();

	// -------------------Retrieve All
	// Products---------------------------------------------

	@RequestMapping(value = "/produto/", method = RequestMethod.GET)
	public ResponseEntity<List<Produto>> listarProdutos(@RequestParam(defaultValue = "id,asc") String[] sort) {
		List<Produto> produtos = produtoService.findAllProdutos(sort);

		if (produtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<>(produtos, HttpStatus.OK);

	}

	// -------------------Criar um Produto-------------------------------------------

	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto, UriComponentsBuilder ucBuilder) {

		try{
			return new ResponseEntity<>(this.produtoService.saveProduto(produto), HttpStatus.CREATED);
		}
		catch(ObjetoJaExistenteException o){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		catch(ObjetoInvalidoException i){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(ucBuilder.path("/api/produto/{id}").buildAndExpand(produto.getId()).toUri());

	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") long id) {

		try {
			return new ResponseEntity<Produto>(produtoService.findById(id), HttpStatus.OK);
		}
		catch (ObjetoInexistenteException e){
			return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateProduto(@PathVariable("id") long id, @RequestBody Produto produto) {

		try {
			return new ResponseEntity<Produto>(produtoService.updateProdutoById(id, produto), HttpStatus.OK);
		}
		catch (ObjetoInexistenteException e) {
			return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		try {
			produtoService.deleteProdutoById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (ObjetoInexistenteException e) {
			return new ResponseEntity<>(new CustomErrorType(e.getMessage()), HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value = "/produto/indisponiveis", method = RequestMethod.GET)
	public ResponseEntity<List<ProdutoClienteDTO>> listarProdutosIndisponiveis() {
		List<ProdutoClienteDTO> produtosIndisponiveis = produtoService.findProdutosIndisponiveis(2);

		return new ResponseEntity<List<ProdutoClienteDTO>>(produtosIndisponiveis, HttpStatus.OK);
	}
  
	@RequestMapping(value = "/produto/disponiveis", method = RequestMethod.GET)
	public ResponseEntity<List<ProdutoClienteDTO>> listarProdutosDisponiveis() {
		List<ProdutoClienteDTO> produtos = produtoService.findProdutosIndisponiveis(1);
    
    	if (produtos.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		

		return new ResponseEntity<List<ProdutoClienteDTO>>(produtos, HttpStatus.OK);
	}

	@RequestMapping(value = "/produto/em-baixa", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutosEmBaixa() {
		List<Produto> produtosEmBaixa = this.produtoService.getProdutosEmBaixa();

		if (produtosEmBaixa.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
    
		return new ResponseEntity<>(produtosEmBaixa, HttpStatus.OK);
	}

}