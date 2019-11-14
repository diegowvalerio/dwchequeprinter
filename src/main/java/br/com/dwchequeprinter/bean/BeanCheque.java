package br.com.dwchequeprinter.bean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;

import br.com.dwchequeprinter.classe.Cheque;
import br.com.dwchequeprinter.msg.FacesMessageUtil;
import br.com.dwchequeprinter.service.ServicoCheque;

@Named
@ViewScoped
public class BeanCheque implements Serializable {
	private static final long serialVersionUID = 1L;

	private Cheque cheque = new Cheque();
	@Inject
	private ServicoCheque servico;
	private List<Cheque> lista;

	/* RELATORIOS */

	public Cheque getCheque() {
		return cheque;
	}

	public void setCheque(Cheque cheque) {
		this.cheque = cheque;
	}

	public List<Cheque> getLista() {
		return lista;
	}

	public void setLista(List<Cheque> lista) {
		this.lista = lista;
	}

	public void imprimecheque() {
	
		Relatorio report = new Relatorio();
		report.imprimecheque(cheque.getNome(), cheque.getValor(), cheque.getCidade(), cheque.getUf());

	}

}
