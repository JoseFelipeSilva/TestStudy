package br.com.senai.testStudy.model;

public class Examinador extends Pessoa {
	private Integer idExaminador;
	private Disciplina disciplinaExaminador;
	
	

	public Disciplina getDisciplinaExaminador() {
		return disciplinaExaminador;
	}

	public void setDisciplinaExaminador(Disciplina disciplinaExaminador) {
		this.disciplinaExaminador = disciplinaExaminador;
	}

	public Integer getIdExaminador() {
		return idExaminador;
	}

	public void setIdExaminador(Integer idExaminador) {
		this.idExaminador = idExaminador;
	}

}
