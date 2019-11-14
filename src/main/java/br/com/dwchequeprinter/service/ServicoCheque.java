package br.com.dwchequeprinter.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.dwchequeprinter.classe.Cheque;
import br.com.dwchequeprinter.dao.DAOCheque;
@Dependent
public class ServicoCheque implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAOCheque dao;
	
	public Cheque imprimecheque(Cheque cheque){
		return dao.imprimecheque(cheque);
	}
	
}
