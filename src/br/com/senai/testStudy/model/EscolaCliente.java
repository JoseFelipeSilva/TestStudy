package br.com.senai.testStudy.model;

public class EscolaCliente {
	Integer idEmp;
	String cnpjEmp;
	String emailEmp;
	String nomeEmp;
	String telefoneEmp;
	String nomeEmpresarialEmp; // CORRESPONDE A RAZÃO SOCIAL DA EMPRESA

	public Integer getIdEmp() {
		return idEmp;
	}

	public void setIdEmp(Integer idEmp) {
		this.idEmp = idEmp;
	}

	public String getCnpjEmp() {
		return cnpjEmp;
	}

	public void setCnpjEmp(String cnpjEmp) {
		this.cnpjEmp = cnpjEmp;
	}

	public String getEmailEmp() {
		return emailEmp;
	}

	public void setEmailEmp(String emailEmp) {
		this.emailEmp = emailEmp;
	}

	public String getNomeEmp() {
		return nomeEmp;
	}

	public void setNomeEmp(String nomeEmp) {
		this.nomeEmp = nomeEmp;
	}

	public String getTelefoneEmp() {
		return telefoneEmp;
	}

	public void setTelefoneEmp(String telefoneEmp) {
		this.telefoneEmp = telefoneEmp;
	}

	public String getNomeEmpresarialEmp() {
		return nomeEmpresarialEmp;
	}

	public void setNomeEmpresarialEmp(String nomeEmpresarialEmp) {
		this.nomeEmpresarialEmp = nomeEmpresarialEmp;
	}

}
