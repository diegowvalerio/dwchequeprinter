package br.com.dwchequeprinter.bean;


import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;


import org.omnifaces.util.Faces;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class Relatorio{
	
	private HttpServletResponse response;
	private FacesContext context;
	private Connection con;
	
	public Relatorio() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) this.context.getExternalContext().getResponse();
	}
	
	public void imprimecheque(String nome, double valor, String cidade, String uf, String valorextenso){
		try{
			String caminho = "";
			caminho = Faces.getRealPath("/pages/reports/cheque/cheque");
		
			JasperCompileManager.compileReportToFile(caminho+".jrxml");
			JasperReport rp = (JasperReport) JRLoader.loadObjectFromFile(caminho+".jasper");
			
			Map<String, Object> params = new HashMap<String, Object>();
			
					
			params.put("NOME", nome);
			params.put("VALOR", valor);
			params.put("VALOREXTENSO", valorextenso);
			params.put("CIDADE", cidade);
			params.put("UF", uf);
			params.put("USUARIO", usuarioconectado());
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperPrint print = JasperFillManager.fillReport(rp, params, getConexao());
			JasperExportManager.exportReportToPdfStream(print, baos);
			
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition","inline; filename=relatorio.pdf");
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			context.responseComplete();
			closeConnection();
			
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao gerar o relatorio!"));
		}
	}
	
	/*clientes*/
	public void rel_clientes_lista(String situacao,String vendedor,String vendedor1, Date data, Date data1,String nome){
		try{
			String caminho = "";
			caminho = Faces.getRealPath("/pages/reports/cheque/cheque");
		
			JasperCompileManager.compileReportToFile(caminho+".jrxml");
			JasperReport rp = (JasperReport) JRLoader.loadObjectFromFile(caminho+".jasper");
			
			Map<String, Object> params = new HashMap<String, Object>();
			boolean b = false;
			if (situacao.equals("A")){
				b = true;
				params.put("SITUACAO", b);
				params.put("SITUACAO1", b);
			}else if (situacao.equals("I")){
				b = false;
				params.put("SITUACAO", b);
				params.put("SITUACAO1", b);
			}else{
				boolean b1 = true;
				params.put("SITUACAO", b);
				params.put("SITUACAO1", b1);
			}
			
			if(vendedor.equals("")){
				params.put("VENDEDOR", 0);
				params.put("VENDEDOR1", 999999999);
			}else{
				params.put("VENDEDOR", Integer.parseInt(vendedor));
				params.put("VENDEDOR1", Integer.parseInt(vendedor1));
			}
			
			if(nome.equals("")){
				params.put("NOME","%%");
			}else{
				params.put("NOME", "%"+nome+"%");
			}
			
			params.put("DATA", data);
			params.put("DATA1", data1);	
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperPrint print = JasperFillManager.fillReport(rp, params, getConexao());
						
			
			JasperExportManager.exportReportToPdfStream(print, baos);
			
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition","inline; filename=relatorio.pdf");
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			context.responseComplete();
			closeConnection();
			
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao gerar o relatorio!"));
		}
		
	}

	/*conexao*/
	private Connection getConexao(){
		try {
			 String connectionUrl = "jdbc:sqlserver://SIGE\\SQLEXPRESS:1433;databaseName=SATLBASE"; 
			 
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl, "sa", "@rv0re24Xcv");
			return con;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String usuarioconectado() {
		String nome;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			nome = ((UserDetails) principal).getUsername();
		} else {
			nome = principal.toString();
		}
		// System.out.println(nome);
		return nome;
	}
}
