package com.ufcg.psoft.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
public class Lote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	@OneToOne(cascade = CascadeType.ALL)
    private Produto produto;

    @Column
    private int numeroDeItens;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataDeValidade;
    
    private static final int ZERO = 0;
    
    public Lote() {
        this.id = ZERO;
    }

    public Lote(Produto produto, int numeroDeItens, LocalDate dataDeValidade) {
        super();
        this.produto = produto;
        this.numeroDeItens = numeroDeItens;
        this.dataDeValidade = dataDeValidade;
    }

    public Lote(long id, Produto produto, int numeroDeItens, LocalDate dataDeValidade) {
        this.id = id;
        this.produto = produto;
        this.numeroDeItens = numeroDeItens;
        this.dataDeValidade = dataDeValidade;
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

    public int getNumeroDeItens() {
        return numeroDeItens;
    }

    public void setNumeroDeItens(int numeroDeItens) {
        this.numeroDeItens = numeroDeItens;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }

    public boolean pertoDoVencimento() {
    	int diaDoMes = 30;
    	boolean pertoVencer = this.vencimentoPrazo(diaDoMes);
    	return pertoVencer;
    }
    
    public boolean vencido() {
    	int diaVencido = -1;
    	boolean vencido = this.vencimentoPrazo(diaVencido);

    	return vencido;
    }

    private boolean vencimentoPrazo(int dia) {
		// TODO Auto-generated method stub
		LocalDate data = this.dataDeValidade;
		LocalDate auxiliarData = LocalDate.now();
		long diferencaDatas = diferencaDias(data, auxiliarData);
		boolean estaVencido = false;
		if (diferencaDatas <= dia) {
			estaVencido = true;
		}
		return estaVencido;
	}

//	private long diferencaDias(Date data, Date auxiliarData) {
//		// TODO Auto-generated method stub
//		long dias = 24L * 60L * 60L * 1000L;
//		long diferenca = ((data.getTime() - auxiliarData.getTime() ) / dias);
//		return diferenca;
//	}

    private long diferencaDias(LocalDate dataLote, LocalDate auxiliarData){
        return DAYS.between(auxiliarData, dataLote);
    }

//	private Date getLoteData() {
//		// TODO Auto-generated method stub
//		String formatoData = "dd/MM/yyyy";
//		SimpleDateFormat formato = new SimpleDateFormat(formatoData);
//		Date dataVencimento = null;
//
//		try {
//			dataVencimento = formato.parse(this.getDataDeValidade());
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return dataVencimento;
//	}
	

    @Override
    public String toString() {
    	String ret = "O lote de número: " + this.id;
		ret += " com número total de itens igual a: " + this.numeroDeItens;
		ret += " e data de validade: " + this.dataDeValidade;
		return ret;
    }
}
