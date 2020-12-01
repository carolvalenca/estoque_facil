package com.ufcg.psoft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.ProdutoServiceImpl;
import com.ufcg.psoft.strategy.DescontoEnum;

import exceptions.ObjetoInexistenteException;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiControllerCategoria {
	
	@Autowired
	private ProdutoService produtoService = new ProdutoServiceImpl();

	@RequestMapping(value = "/categoria/{categoria}/desconto/{desconto}", method = RequestMethod.POST)
	public ResponseEntity<?> atribuiDescontoACategoria(@PathVariable("categoria") String nomeCategoria, @PathVariable("desconto") DescontoEnum tipoDesconto)
			throws ObjetoInexistenteException {
		this.produtoService.mudaCategoria(nomeCategoria, tipoDesconto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
