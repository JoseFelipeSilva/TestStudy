package br.com.senai.testStudy.model;

import java.sql.Date;
import java.util.List;

public class Prova {

	private Integer idProva;
	private Professor professor;
	private Integer dificuldadeDE;
	private Integer dificuldadeATE;
	private Integer nQuestoes;
	private String nomeProva;
	private Date criacaoProva;
	private Materia[] materias;
	private List<QuestaoProva> questoes;

	public Materia[] getMaterias() {
		return materias;
	}

	public void setMaterias(Materia[] materias) {
		this.materias = materias;
	}

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

	public Integer getDificuldadeDE() {
		return dificuldadeDE;
	}

	public void setDificuldadeDE(Integer dificuldadeDE) {
		this.dificuldadeDE = dificuldadeDE;
	}

	public Integer getDificuldadeATE() {
		return dificuldadeATE;
	}

	public void setDificuldadeATE(Integer dificuldadeATE) {
		this.dificuldadeATE = dificuldadeATE;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Integer getnQuestoes() {
		return nQuestoes;
	}

	public void setnQuestoes(Integer nQuestoes) {
		this.nQuestoes = nQuestoes;
	}

	public Date getCriacaoProva() {
		return criacaoProva;
	}

	public void setCriacaoProva(Date criacaoProva) {
		this.criacaoProva = criacaoProva;
	}

	public List<QuestaoProva> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<QuestaoProva> questoes) {
		this.questoes = questoes;
	}

}
