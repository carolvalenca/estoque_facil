package com.ufcg.psoft.strategy;

import java.math.BigDecimal;

import com.ufcg.psoft.model.Categoria;

public class SuperDesconto implements DescontoStrategy {

	@Override
	public void aplicaDesconto(Categoria categoria) {
		
		categoria.setTipoDesconto(DescontoEnum.SUPER_DESCONTO);
		categoria.setDesconto(new BigDecimal(0.50));
		
	}

	
}
