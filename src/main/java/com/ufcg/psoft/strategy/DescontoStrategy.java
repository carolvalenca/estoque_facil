package com.ufcg.psoft.strategy;

import com.ufcg.psoft.model.Categoria;

public interface DescontoStrategy {

    void aplicaDesconto(Categoria categoria);
}
