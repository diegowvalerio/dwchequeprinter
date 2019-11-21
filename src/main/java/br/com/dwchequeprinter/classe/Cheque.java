package br.com.dwchequeprinter.classe;

import java.io.Serializable;
import java.util.Date;

public class Cheque implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private double valor;
	private String cidade;
	private String uf;
	private Date data;
	private boolean cruzar;
	
		
	public Cheque() {
		super();
	}
	
	public boolean isCruzar() {
		return cruzar;
	}

	public void setCruzar(boolean cruzar) {
		this.cruzar = cruzar;
	}

	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public double getValor() {
		return valor;
	}


	public void setValor(double valor) {
		this.valor = valor;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getUf() {
		return uf;
	}


	public void setUf(String uf) {
		this.uf = uf;
	}

   
}
