package com.ufcg.psoft.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "produto", nullable = false)
	private List<ProdutoVenda> produtos;
	
	@Column
	private BigDecimal valorTotal;
	
	public Venda() {
		this.id = 0;
		this.valorTotal = new BigDecimal(0);
	}
	
	public Venda(List<ProdutoVenda> produtos, BigDecimal valorTotal) {
		super();
		this.produtos = produtos;
		this.valorTotal = valorTotal;
	}
	
	public Venda(long id, List<ProdutoVenda> produtos, BigDecimal valorTotal) {
		this.id = id;
		this.produtos = produtos;
		this.valorTotal = valorTotal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<ProdutoVenda> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoVenda> produtos) {
		this.produtos = produtos;
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
		result = prime * result + ((produtos == null) ? 0 : produtos.hashCode());
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
		Venda other = (Venda) obj;
		if (id != other.id)
			return false;
		if (produtos == null) {
			if (other.produtos != null)
				return false;
		} else if (!produtos.equals(other.produtos))
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
		int quantidades = produtos.size();
		String ret;
		ret = "Compradas " + quantidades + " unidade(s) de:" + "\n";
		for (ProdutoVenda produtoVenda : produtos) {
			ret += produtoVenda.getProduto() + " ";
		}
		ret += "o custo total da compra foi de R$ " + String.format("%.2f", this.valorTotal) + ".";
		return ret;
	}
	

	

}
