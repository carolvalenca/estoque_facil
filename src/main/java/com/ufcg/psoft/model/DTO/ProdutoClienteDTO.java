package com.ufcg.psoft.model.DTO;

import java.math.BigDecimal;

import javax.persistence.Column;

import com.ufcg.psoft.model.Categoria;
import com.ufcg.psoft.model.Produto;

public class ProdutoClienteDTO {

    private String nome;
    private Categoria categoria;
    private BigDecimal preco;
    private String codigoBarra;
	private String fabricante;
	private int qtdProdutosDisponiveis;
	
	public ProdutoClienteDTO(String nome, Categoria categoria, BigDecimal preco, String codigoBarra, String fabricante, int qtdProdutosDisponiveis) {
		this.nome = nome;
		this.categoria = categoria;
		this.preco = preco;
		this.codigoBarra = codigoBarra;
		this.fabricante = fabricante;
		this.qtdProdutosDisponiveis = qtdProdutosDisponiveis;
	}
	
	public ProdutoClienteDTO(Produto produto, int qtdProdutosDisponiveis) {
		this.nome = produto.getNome();
		this.categoria = produto.getCategoria();
		this.preco = produto.getPreco();
		this.codigoBarra = produto.getCodigoBarra();
		this.fabricante = produto.getFabricante();
		this.qtdProdutosDisponiveis = qtdProdutosDisponiveis;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public int getQtdProdutosDisponiveis() {
		return qtdProdutosDisponiveis;
	}

	public void setQtdProdutosDisponiveis(int qtdProdutosDisponiveis) {
		this.qtdProdutosDisponiveis = qtdProdutosDisponiveis;
	}
}

