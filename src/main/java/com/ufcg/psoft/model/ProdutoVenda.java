package com.ufcg.psoft.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ProdutoVenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Produto produto;
	
	@Column
	private int qtdItens;
	
	@Column
	private BigDecimal valorTotal;
	
	public ProdutoVenda() {
		this.id = 0;
		this.valorTotal = new BigDecimal(0);
	}
	
	public ProdutoVenda(Produto produto, int qtdItens, BigDecimal valorTotal) {
		super();
		this.produto = produto;
		this.qtdItens = qtdItens;
		this.valorTotal = valorTotal;
	}
	
	public ProdutoVenda(long id, Produto produto, int qtdItens, BigDecimal valorTotal) {
		this.id = id;
		this.produto = produto;
		this.qtdItens = qtdItens;
		this.valorTotal = valorTotal;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQtdItens() {
		return qtdItens;
	}

	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + qtdItens;
		result = prime * result + ((valorTotal == null) ? 0 : valorTotal.hashCode());
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
		ProdutoVenda other = (ProdutoVenda) obj;
		if (id != other.id)
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (qtdItens != other.qtdItens)
			return false;
		if (valorTotal == null) {
			if (other.valorTotal != null)
				return false;
		} else if (!valorTotal.equals(other.valorTotal))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		String ret = "O produto " + this.getProduto().getNome() + " teve " + this.qtdItens + 
				" de itens vendidos, com um preço total de R$" + this.valorTotal +";";
		return ret;
	}

}
