package com.ufcg.psoft.strategy;

import java.math.BigDecimal;

import com.ufcg.psoft.model.Categoria;

public class SemDesconto implements DescontoStrategy {

	@Override
	public void aplicaDesconto(Categoria categoria) {
		
		categoria.setTipoDesconto(DescontoEnum.SEM_DESCONTO);

	}

}
