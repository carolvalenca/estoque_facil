package com.ufcg.psoft.strategy;

import java.math.BigDecimal;

import com.ufcg.psoft.model.Categoria;

public class BomDesconto implements DescontoStrategy {

	@Override
	public void aplicaDesconto(Categoria categoria) {
		
		categoria.setTipoDesconto(DescontoEnum.BOM_DESCONTO);
		categoria.setDesconto(new BigDecimal(0.10));
		
	}

}
