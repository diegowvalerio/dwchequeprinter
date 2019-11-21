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

import org.apache.commons.lang.StringUtils;
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
		String ex =valorPorExtenso(cheque.getValor());
		//int count = 300 - ex.length();
		String extenso = String.format ("%25.25s", "")+StringUtils.rightPad(ex, 195, " #");
		//extenso = String.format ("%20.20s", "")+extenso;
				
		Relatorio report = new Relatorio();
		report.imprimecheque(cheque.getNome().toUpperCase(), cheque.getValor(), cheque.getCidade().toUpperCase(), extenso.toUpperCase(), cheque.getData(), cheque.isCruzar());

	}
	
	public static String valorPorExtenso(double vlr) {
		    if (vlr == 0)
		       return("zero");
		 
		    long inteiro = (long)Math.abs(vlr); // parte inteira do valor
		    double resto = vlr - inteiro;       // parte fracion�ria do valor
		 
		    String vlrS = String.valueOf(inteiro);
		    if (vlrS.length() > 15)
		       return("Erro: valor superior a 999 trilh�es.");
		 
		    String s = "", saux, vlrP;
		    String centavos = String.valueOf((int)Math.round(resto * 100));
		 
		    String[] unidade = {"", "um", "dois", "tr�s", "quatro", "cinco",
		             "seis", "sete", "oito", "nove", "dez", "onze",
		             "doze", "treze", "quatorze", "quinze", "dezesseis",
		             "dezessete", "dezoito", "dezenove"};
		    String[] centena = {"", "cento", "duzentos", "trezentos",
		             "quatrocentos", "quinhentos", "seiscentos",
		             "setecentos", "oitocentos", "novecentos"};
		    String[] dezena = {"", "", "vinte", "trinta", "quarenta", "cinquenta",
		             "sessenta", "setenta", "oitenta", "noventa"};
		    String[] qualificaS = {"", "mil", "milh�o", "bilh�o", "trilh�o"};
		    String[] qualificaP = {"", "mil", "milh�es", "bilh�es", "trilh�es"};
		 
		// definindo o extenso da parte inteira do valor
		    int n, unid, dez, cent, tam, i = 0;
		    boolean umReal = false, tem = false;
		    while (!vlrS.equals("0")) {
		      tam = vlrS.length();
		// retira do valor a 1a. parte, 2a. parte, por exemplo, para 123456789:
		// 1a. parte = 789 (centena)
		// 2a. parte = 456 (mil)
		// 3a. parte = 123 (milh�es)
		      if (tam > 3) {
		         vlrP = vlrS.substring(tam-3, tam);
		         vlrS = vlrS.substring(0, tam-3);
		      }
		      else { // �ltima parte do valor
		        vlrP = vlrS;
		        vlrS = "0";
		      }
		      if (!vlrP.equals("000")) {
		         saux = "";
		         if (vlrP.equals("100"))
		            saux = "cem";
		         else {
		           n = Integer.parseInt(vlrP, 10);  // para n = 371, tem-se:
		           cent = n / 100;                  // cent = 3 (centena trezentos)
		           dez = (n % 100) / 10;            // dez  = 7 (dezena setenta)
		           unid = (n % 100) % 10;           // unid = 1 (unidade um)
		           if (cent != 0)
		              saux = centena[cent];
		           if ((n % 100) <= 19) {
		              if (saux.length() != 0)
		                 saux = saux + " e " + unidade[n % 100];
		              else saux = unidade[n % 100];
		           }
		           else {
		              if (saux.length() != 0)
		                 saux = saux + " e " + dezena[dez];
		              else saux = dezena[dez];
		              if (unid != 0) {
		                 if (saux.length() != 0)
		                    saux = saux + " e " + unidade[unid];
		                 else saux = unidade[unid];
		              }
		           }
		         }
		         if (vlrP.equals("1") || vlrP.equals("001")) {
		            if (i == 0) // 1a. parte do valor (um real)
		               umReal = true;
		            else saux = saux + " " + qualificaS[i];
		         }
		         else if (i != 0)
		                 saux = saux + " " + qualificaP[i];
		         if (s.length() != 0)
		            s = saux + ", " + s;
		         else s = saux;
		      }
		      if (((i == 0) || (i == 1)) && s.length() != 0)
		         tem = true; // tem centena ou mil no valor
		      i = i + 1; // pr�ximo qualificador: 1- mil, 2- milh�o, 3- bilh�o, ...
		    }
		 
		    if (s.length() != 0) {
		       if (umReal)
		          s = s + " real";
		       else if (tem)
		               s = s + " reais";
		            else s = s + " de reais";
		    }
		 
		// definindo o extenso dos centavos do valor
		    if (!centavos.equals("0")) { // valor com centavos
		       if (s.length() != 0) // se n�o � valor somente com centavos
		          s = s + " e ";
		       if (centavos.equals("1"))
		          s = s + "um centavo";
		       else {
		         n = Integer.parseInt(centavos, 10);
		         if (n <= 19)
		            s = s + unidade[n];
		         else {             // para n = 37, tem-se:
		           unid = n % 10;   // unid = 37 % 10 = 7 (unidade sete)
		           dez = n / 10;    // dez  = 37 / 10 = 3 (dezena trinta)
		           s = s + dezena[dez];
		           if (unid != 0)
		              s = s + " e " + unidade[unid];
		         }
		         s = s + " centavos";
		       }
		    }
		    return(s);
	}

}
