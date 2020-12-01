package com.ufcg.psoft.model.DTO;

public class ProdutoVendaDTO {
	private long idProduto;
	private int qtdItens;
	
	public ProdutoVendaDTO() {}
	
	public ProdutoVendaDTO(long idProduto, int qtdItens) {
		this.idProduto = idProduto;
		this.qtdItens = qtdItens;
	}

	public long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(long idProduto) {
		this.idProduto = idProduto;
	}

	public int getQtdItens() {
		return qtdItens;
	}

	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}
	
}
