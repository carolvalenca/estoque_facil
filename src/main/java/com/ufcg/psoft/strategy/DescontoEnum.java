package com.ufcg.psoft.strategy;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum DescontoEnum {
	
	SEM_DESCONTO, BOM_DESCONTO, OTIMO_DESCONTO, SUPER_DESCONTO;


	@JsonCreator
	public static DescontoEnum decode(final String code) {
		return Stream.of(DescontoEnum.values()).filter(targetEnum -> targetEnum.name().equals(code)).findFirst().orElse(null);
	}
	
}
