package com.ufcg.psoft.service;

import java.util.*;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.DTO.ProdutoClienteDTO;
import com.ufcg.psoft.model.Lote;
import com.ufcg.psoft.repositories.CategoriaRepository;
import com.ufcg.psoft.repositories.ProdutoRepository;
import com.ufcg.psoft.strategy.DescontoEnum;
import com.ufcg.psoft.strategy.DescontoFactory;
import com.ufcg.psoft.strategy.DescontoStrategy;
import com.ufcg.psoft.util.OrderHelper;
import exceptions.ObjetoInexistenteException;
import exceptions.ObjetoInvalidoException;
import exceptions.ObjetoJaExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.model.Produto;

@Service("produtoService")
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private DescontoFactory descontoFactory;
	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Produto> findAllProdutos() {
		return produtoRepository.findAll();
	}

	@Override
	public List<Produto> findAllProdutos(String[] sort) {
		List<Order> ordersList = OrderHelper.getSortingOrders(sort);
		return this.produtoRepository.findAll(Sort.by(ordersList));
	}


	public Produto saveProduto(Produto produto) throws ObjetoJaExistenteException, ObjetoInvalidoException {
		Produto p = this.produtoRepository.findById(produto.getId())
				.orElseThrow(()-> new ObjetoJaExistenteException("Product with id: " + produto.getId() + "already exists"));

		p.mudaSituacao(Produto.INDISPONIVEL);

		return this.produtoRepository.save(p);
	}

	@Override
	public void updateProduto(Produto produto) {
		this.produtoRepository.save(produto);
	}

	@Override
	public Produto updateProdutoById(Long id, Produto produto) throws ObjetoInexistenteException {
		Produto p = this.produtoRepository.findById(id)
				.orElseThrow(() -> new ObjetoInexistenteException("product with id: " + id + " not found" ));

		p.setCodigoBarra(produto.getCodigoBarra());
		p.mudaNome(produto.getNome());
		p.setPreco(produto.getPreco());
		p.mudaFabricante(produto.getFabricante());
		p.mudaCategoria(produto.getCategoria());

		return this.produtoRepository.save(p);

	}

	public void deleteProdutoById(long id) throws ObjetoInexistenteException {
		Produto produto = this.produtoRepository.findById(id)
				.orElseThrow(()-> new ObjetoInexistenteException("Product with id: " + id + " not found"));
		this.produtoRepository.deleteById(id);
	}

	public int size() {
		return this.produtoRepository.findAll().size();
	}

	public Iterator<Produto> getIterator() {
		Iterable<Produto> produtos = this.produtoRepository.findAll();
		return produtos.iterator();
	}

	public void deleteAllUsers() {
		produtoRepository.deleteAll();
	}

	public Produto findById(long id) throws ObjetoInexistenteException {
		Produto produto = this.produtoRepository.getProdutoById(id);

		if (produto == null) {
			throw new ObjetoInexistenteException("Produto nao existe");
		}
		return produto;
	}

	public boolean doesProdutoExist(Produto produto) {
		long id = produto.getId();

		boolean produtoExiste = this.produtoRepository.existsById(id);

		return produtoExiste;
	}
	
	@Override
	public List<ProdutoClienteDTO> findProdutosIndisponiveis(int situacao) {
		List<Produto> produtos = produtoRepository.findBySituacao(situacao);

		List<ProdutoClienteDTO> produtosCliente = new ArrayList<ProdutoClienteDTO>();

		for (Produto produto : produtos) {
			Lote loteProduto = produto.getLote();
			produtosCliente.add(new ProdutoClienteDTO(produto, loteProduto.getNumeroDeItens()));
		}

		return produtosCliente;
	}

	public List<String> relatorioProdutos() {
		// TODO Auto-generated method stub
		List<Produto> produtos = this.findAllProdutos();
		List<String> resultado = new LinkedList<String>();
		for (Produto produto : produtos) {
			resultado.add(produto.toString());
		}
		return resultado;
	}

	@Override
	public Categoria mudaCategoria(String nomeCategoria, DescontoEnum tipoDesconto) {
		DescontoStrategy strategy = descontoFactory.getStrategy(tipoDesconto);
		Categoria categoria = categoriaRepository.getCategoriaByNome(nomeCategoria);
		strategy.aplicaDesconto(categoria);
		return categoriaRepository.save(categoria);
	}

	@Override
	public List<Produto> getProdutosEmBaixa(){
		return this.produtoRepository.findProdutosEmBaixa();
	}

}
