package com.ufcg.psoft.controller;

import com.ufcg.psoft.model.DTO.LoteDTO;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;
import com.ufcg.psoft.service.LoteService;
import com.ufcg.psoft.service.LoteServiceImpl;
import com.ufcg.psoft.service.ProdutoService;
import com.ufcg.psoft.service.ProdutoServiceImpl;
import com.ufcg.psoft.util.CustomErrorType;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoInvalidoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RestApiControllerLote {

	@Autowired
	LoteService loteService = new LoteServiceImpl();
	@Autowired
	ProdutoService produtoService = new ProdutoServiceImpl();
	
	private final static int ZERO = 0;
	
	@RequestMapping(value = "/produto/{id}/lote", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@PathVariable("id") long produtoId, @RequestBody LoteDTO loteDTO) throws ObjetoInexistenteException, ObjetoInvalidoException {
		Produto product = produtoService.findById(produtoId);

		if (product == null) {
			return new ResponseEntity(
					new CustomErrorType("Unable to create lote. Produto with id " + produtoId + " not found."),
					HttpStatus.NOT_FOUND);
		}

		mudaDisponibilidadeLote(product, loteDTO);

		Lote lote = loteService.saveLote(new Lote(product, loteDTO.getNumeroDeItens(), loteDTO.getDataDeValidade()));

		return new ResponseEntity<>(lote, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/produto/{id}/lote", method = RequestMethod.PUT)
	public ResponseEntity<?> alterarLote(@PathVariable("id") long produtoId, @RequestBody LoteDTO loteDTO) throws ObjetoInexistenteException, ObjetoInvalidoException {
		Lote lote = loteService.findLoteByProdutoId(produtoId);

		if (lote == null) {
			return new ResponseEntity(
					new CustomErrorType("Unable to update. Lote of produto with id " + produtoId + " not found."),
					HttpStatus.NOT_FOUND);
		}

		Produto produto = lote.getProduto();
		mudaDisponibilidadeLote(produto, loteDTO);
		
		lote.setNumeroDeItens(loteDTO.getNumeroDeItens());
		lote.setDataDeValidade(loteDTO.getDataDeValidade());
		loteService.updateLote(lote);

		return new ResponseEntity<>(lote, HttpStatus.OK);
	}


	@RequestMapping(value = "/lote/", method = RequestMethod.GET)
	public ResponseEntity<List<Lote>> listAllLotes(@RequestParam(defaultValue = "dataDeValidade, asc") String[] sort) {
		List<Lote> lotes = loteService.findAllLotes(sort);

		if (lotes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<>(lotes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lista-perto-vencer", method = RequestMethod.GET)
    public ResponseEntity<?> listaPertoVencer(){
    	List<Produto> produtosPertosVencer = this.loteService.listaPertoVencer();
    	if(produtosPertosVencer.isEmpty()) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<>(produtosPertosVencer, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/lista-vencidos", method = RequestMethod.GET)
    public ResponseEntity<?> listaLotesVencidos(){
    	List<Produto> produtosVencidos = this.loteService.listaVencidos();
    	
    	if(produtosVencidos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	
    	return new ResponseEntity<>(produtosVencidos, HttpStatus.OK);
    }

    private void mudaDisponibilidadeLote(Produto produto, LoteDTO loteDTO) throws ObjetoInvalidoException {
		if (produto.getSituacao() == Produto.INDISPONIVEL) {
			if (loteDTO.getNumeroDeItens() > ZERO) {
				produto.situacao = Produto.DISPONIVEL;
				produtoService.updateProduto(produto);
			}
		}
		if (produto.getSituacao() == Produto.DISPONIVEL) {
			if (loteDTO.getNumeroDeItens() <= ZERO) {
				produto.situacao = Produto.INDISPONIVEL;
				produtoService.updateProduto(produto);
			}
		}
	}
}
