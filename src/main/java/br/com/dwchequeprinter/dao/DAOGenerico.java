package br.com.dwchequeprinter.dao;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.dwchequeprinter.classe.Cheque;

public interface DAOGenerico<E> {
	
	public Cheque imprimecheque(Cheque cheque);
	
	
}
