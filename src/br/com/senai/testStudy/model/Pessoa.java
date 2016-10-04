package br.com.senai.testStudy.model;

import java.sql.Date;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

abstract class Pessoa {
	private String nome;
	private String sexo;
	private String email;
	private String cpf;
	private String rg;
	private Date nascimento;
	private String senha;
	private byte[] foto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	// METODO DE RETORNA A FOTO EM CODIGO
	public String getFoto64() {
		String encodedImage = Base64.encode(foto);
		return encodedImage;
	}

}
