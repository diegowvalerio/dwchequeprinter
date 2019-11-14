package br.com.dwchequeprinter.classe;

import java.io.Serializable;

public class Cheque implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private double valor;
	private String cidade;
	private String uf;
	
		
	public Cheque() {
		super();
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
