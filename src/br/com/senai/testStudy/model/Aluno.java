package br.com.senai.testStudy.model;

import java.sql.Date;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Aluno {
	private Integer idAluno;
	private String nomeAluno;
	private String email;
	private String rgAluno;
	private String senha;
	private String sexoAluno;
	private Date nascAluno;
	private Turma turmaAluno;
	private byte[] fotoAluno;

	public Integer getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Integer idAluno) {
		this.idAluno = idAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRgAluno() {
		return rgAluno;
	}

	public void setRgAluno(String rgAluno) {
		this.rgAluno = rgAluno;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexoAluno() {
		return sexoAluno;
	}

	public void setSexoAluno(String sexoAluno) {
		this.sexoAluno = sexoAluno;
	}

	public Date getNascAluno() {
		return nascAluno;
	}

	public void setNascAluno(Date nascAluno) {
		this.nascAluno = nascAluno;
	}

	public Turma getTurmaAluno() {
		return turmaAluno;
	}

	public void setTurmaAluno(Turma turmaAluno) {
		this.turmaAluno = turmaAluno;
	}

	public byte[] getFotoAluno() {
		return fotoAluno;
	}

	public void setFotoAluno(byte[] fotoAluno) {
		this.fotoAluno = fotoAluno;
	}

	// METODO DE RETORNA A FOTO EM CODIGO
	public String getFoto64() {
		String encodedImage = Base64.encode(fotoAluno);
		return encodedImage;
	}
}
