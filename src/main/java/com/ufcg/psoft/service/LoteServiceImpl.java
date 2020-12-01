package com.ufcg.psoft.service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ufcg.psoft.repositories.LoteRepository;
import com.ufcg.psoft.repositories.ProdutoRepository;

import com.ufcg.psoft.util.OrderHelper;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoInvalidoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.model.Produto;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Lote saveLote(Lote lote) {
		Lote savedLote = loteRepository.save(lote);

		return savedLote;
	}


	@Override
	public Lote findById(long id) throws ObjetoInexistenteException {
		Lote lote = findById(id);

		if (lote == null) {
			throw new ObjetoInexistenteException("Lote nao existe");
		}

		return lote;
	}

	@Override
	public void updateLote(Lote lote) {
		this.loteRepository.save(lote);
	}

	@Override
	public void deleteLoteById(long id) {
		this.loteRepository.deleteById(id);
	}

	@Override
	public List<Lote> findAllLotes() {
		List<Lote> lotesList = this.loteRepository.findAll();

		return lotesList;
	}

	@Override
	public List<Lote> findAllLotes(String[] sort) {
		List<Order> sortList = OrderHelper.getSortingOrders(sort);

		return this.loteRepository.findAll(Sort.by(sortList));
	}

	@Override
	public int size() {
		List<Lote> lotes = this.findAllLotes();
		int size = lotes.size();

		return size;
	}

	@Override
	public Iterator<Lote> getIterator() {
		Iterable<Lote> lotes = this.findAllLotes();
		Iterator<Lote> iterator = lotes.iterator();

		return iterator;
	}

	@Override
	public List<Produto> listaPertoVencer() {
		// TODO Auto-generated method stub
		List<Produto> produtosPertoVencer = new LinkedList<>();
		List<Produto> produtos = this.produtoRepository.findAll();
		for(Produto produto: produtos) {
				boolean proximoVencer = this.lotePertoVencer(produto);
				if(proximoVencer) {
					produtosPertoVencer.add(produto);
				}
		}
		return produtosPertoVencer;
	}

	@Override
	public List<Produto> listaVencidos() {
		// TODO Auto-generated method stub
		List<Produto> produtos = this.produtoRepository.findAll();
		List<Produto> produtosVencidos = new LinkedList<Produto>();
		List<Lote> lotes = this.findAllLotes();

		for(Produto produto: produtos) {
			boolean proximoVencer = this.loteVencido(produto);
			if(proximoVencer) {
				produtosVencidos.add(produto);
			}
		}
		return produtosVencidos;
	}
	
	private boolean loteVencido(Produto produto) {
		// TODO Auto-generated method stub
		List<Lote> lotes = this.findAllLotes();
		int valorIndisponivel = 2;
		boolean loteVencido = false;
		for (Lote lote : lotes) {
			if (lote.getProduto().equals(produto) && lote.vencido()) {
				loteVencido = true;
				try {
					produto.mudaSituacao(valorIndisponivel);
				} catch (ObjetoInvalidoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return loteVencido;
	}

	private boolean lotePertoVencer(Produto produto) {
		// TODO Auto-generated method stub
		List<Lote> lotes = this.findAllLotes();
		boolean lotePertoVencer = false;
		for (Lote lote : lotes) {
			if(lote.getProduto().equals(produto) && lote.pertoDoVencimento()) {
				lotePertoVencer = true;
			}
		}

		return lotePertoVencer;
	}

	@Override
	public Lote findLoteByProdutoId(Long id) {
		Lote lote = loteRepository.findByProdutoId(id);
		
		return lote;
	}


	@Override
	public String relatorioLotes() {
		List<Lote> lotes = this.findAllLotes();
		int totalLotes = lotes.size();
		String pulaLinha = "\n";
		String resultado = "Temos um total de " + 
							totalLotes + " de lotes." + pulaLinha + 
							"Possuiamos as seguintes informações a cerca os lotes:" + pulaLinha;
		for (Lote lote : lotes) {
			resultado += lote.toString();
			resultado += pulaLinha;
		}
		return resultado;
	}
	
	

}
