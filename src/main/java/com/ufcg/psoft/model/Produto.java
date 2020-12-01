package com.ufcg.psoft.model;

import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import exceptions.ObjetoInvalidoException;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String nome;

	@Column
	private BigDecimal preco;

	@Column
	private String codigoBarra;

	@Column
	private String fabricante;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Categoria categoria;

	@OneToOne(mappedBy = "produto")
	@JsonIgnore
	private Lote lote;

	/*
	@Column
	private String categoria;
	 */
	@Column
	public int situacao; // usa variaveis estaticas abaixo
	/* situacoes do produto */
	public static final int DISPONIVEL = 1;
	public static final int INDISPONIVEL = 2;

	public Produto() {
		this.id = 0;
		this.preco = new BigDecimal(0);
		this.categoria =  new Categoria();
	}

	public Produto(long id, String nome, String codigoBarra, String fabricante,
			String nomeCategoria) {
		this.id = id;
		this.nome = nome;
		this.preco = new BigDecimal(0);
		this.codigoBarra = codigoBarra;
		this.fabricante = fabricante;
		this.categoria = new Categoria(nomeCategoria);
		this.situacao = Produto.INDISPONIVEL;
	}

	public String getNome() {
		return nome;
	}

	public Lote getLote() {
		return lote;
	}

	public void mudaNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return this.preco;
	}
	
	public BigDecimal getPrecoComDesconto() {
		return this.preco.subtract(this.categoria.getDesconto().multiply(this.preco));
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public long getId() {
		return id;
	}

	public void mudaId(long codigo) {
		this.id = codigo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void mudaFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void mudaCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void mudaSituacao(int situacao) throws ObjetoInvalidoException {
		switch (situacao) {
		case 1:
			this.situacao = Produto.DISPONIVEL;
			break;
		case 2:
			this.situacao = Produto.INDISPONIVEL;
			break;

		default:
			throw new ObjetoInvalidoException("Situacao Invalida");
		}
	}

	public int getSituacao() throws ObjetoInvalidoException {
		return this.situacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		String ret = "O produto " + this.nome + " com código de barra " + this.codigoBarra;
		ret += " foi fabricado por " + this.fabricante + " no valor de R$" + String.format("%.2f", this.preco);
		return ret;
	}
}
