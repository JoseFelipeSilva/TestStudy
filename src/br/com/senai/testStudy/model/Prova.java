package br.com.senai.testStudy.model;

public class Prova {

	Integer idProva;
	Professor professorprova;
	String dificuldade;
	String nQuestoes;
	String nomeProva;

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

	public Professor getProfessorprova() {
		return professorprova;
	}

	public void setProfessorprova(Professor professorprova) {
		this.professorprova = professorprova;
	}

	public String getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(String dificuldade) {
		this.dificuldade = dificuldade;
	}

	public String getnQuestoes() {
		return nQuestoes;
	}

	public void setnQuestoes(String nQuestoes) {
		this.nQuestoes = nQuestoes;
	}

}
