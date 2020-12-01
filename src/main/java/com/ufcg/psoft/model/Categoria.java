package com.ufcg.psoft.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ufcg.psoft.strategy.DescontoEnum;
import com.ufcg.psoft.strategy.DescontoFactory;


@Entity
public class Categoria implements Comparable<Categoria>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;
	
	@Column
	private String nome;
	
	@Column
	private BigDecimal desconto;
	
	private DescontoEnum tipoDesconto;

	private static final String NOME_SEM_DESCONTO = "Sem Desconto";
	private static final DescontoEnum SEM_DESCONTO = DescontoEnum.SEM_DESCONTO;
	
	public static final int ZERO = 0;
	
	public Categoria(){
		this.nome = NOME_SEM_DESCONTO;
		this.tipoDesconto = SEM_DESCONTO;
		this.desconto =  new BigDecimal(ZERO);
	}
	
	public Categoria(String nome) {
		this.nome = nome;
		this.tipoDesconto = SEM_DESCONTO;
		this.desconto = new BigDecimal(ZERO);
	}
	
	public Categoria(DescontoEnum tipoDesconto, String nome ) {
		this.nome = nome;
		this.tipoDesconto = tipoDesconto;
		this.desconto = new BigDecimal(ZERO);
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setCategoria(String categoria) {
		this.nome = categoria;
	}
	
	public BigDecimal getDesconto() {
		return desconto;
	}
	
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	
	public DescontoEnum setTipoDesconto(DescontoEnum tipoDesconto) {
		return this.tipoDesconto = tipoDesconto;
	}
	
	@Override
	public int compareTo(Categoria categoria) {
		return this.nome.compareTo(categoria.getNome());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (desconto == null) {
			if (other.desconto != null)
				return false;
		} else if (!desconto.equals(other.desconto))
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
