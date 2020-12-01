package com.ufcg.psoft.strategy;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DescontoFactory {

	private Map<DescontoEnum, DescontoStrategy> strategies = new EnumMap<>(DescontoEnum.class);
	
	public DescontoFactory() {
		initStrategies();
	}

	private void initStrategies() {
		
		strategies.put(DescontoEnum.SEM_DESCONTO, new SemDesconto());
		strategies.put(DescontoEnum.BOM_DESCONTO, new BomDesconto());
		strategies.put(DescontoEnum.OTIMO_DESCONTO, new OtimoDesconto());
		strategies.put(DescontoEnum.SUPER_DESCONTO, new SuperDesconto());
		
	}
	
	public DescontoStrategy getStrategy(DescontoEnum descontoEnum) {
		if(descontoEnum == null || !strategies.containsKey(descontoEnum)) {
			throw new IllegalArgumentException("invalid " + descontoEnum);
		}
		return strategies.get(descontoEnum);
	}
}
