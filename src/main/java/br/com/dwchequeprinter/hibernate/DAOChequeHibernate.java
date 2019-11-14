package br.com.dwchequeprinter.hibernate;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import br.com.dwchequeprinter.classe.Cheque;
import br.com.dwchequeprinter.dao.DAOCheque;

@Dependent
public class DAOChequeHibernate extends DAOGenericoHibernate<Cheque> implements DAOCheque,Serializable {
	private static final long serialVersionUID = 1L;
	
	public DAOChequeHibernate(){
		super(Cheque.class);
	}


}
