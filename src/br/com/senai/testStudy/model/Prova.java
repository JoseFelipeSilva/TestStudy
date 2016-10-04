package br.com.senai.testStudy.model;

import java.sql.Date;

public class Prova {

	private Integer idProva;
	private Professor professor;
	private String dificuldade;
	private Integer nQuestoes;
	private String nomeProva;
	private Date dataProva;

	public String getNomeProva() {
		return nomeProva;
	}

	public void setNomeProva(String nomeProva) {
		this.nomeProva = nomeProva;
	}

	public Integer getIdProva() {
		return idProva;
	}

	public void setIdProva(Integer idProva) {
		this.idProva = idProva;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public String getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(String dificuldade) {
		this.dificuldade = dificuldade;
	}

	public Integer getnQuestoes() {
		return nQuestoes;
	}

	public void setnQuestoes(Integer nQuestoes) {
		this.nQuestoes = nQuestoes;
	}

	public Date getDataProva() {
		return dataProva;
	}

	public void setDataProva(Date dataProva) {
		this.dataProva = dataProva;
	}

}
