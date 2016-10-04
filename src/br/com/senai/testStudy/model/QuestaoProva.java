package br.com.senai.testStudy.model;

import java.sql.Date;

public class QuestaoProva {
	Integer idQuestaoProva;
	Integer usoQuestao; // Quantas vezes a questão já foi usada
	Date ultimoUsoQuestao;
	String visualizacaoQuestao; // Mostra se a questão está disponível para ser
								// usada novamente
	String corpoQuestao;
	String tituloQuestao;
	String tipoQuestao; // Se a questão é de alternativa ou dissertativa
	Disciplina disciplina;
	Materia materia;
	String dificuldadeQuestao;

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public String getDificuldadeQuestao() {
		return dificuldadeQuestao;
	}

	public void setDificuldadeQuestao(String dificuldadeQuestao) {
		this.dificuldadeQuestao = dificuldadeQuestao;
	}

	public Integer getIdQuestaoProva() {
		return idQuestaoProva;
	}

	public void setIdQuestaoProva(Integer idQuestaoProva) {
		this.idQuestaoProva = idQuestaoProva;
	}

	public Integer getUsoQuestao() {
		return usoQuestao;
	}

	public void setUsoQuestao(Integer usoQuestao) {
		this.usoQuestao = usoQuestao;
	}

	public Date getUltimoUsoQuestao() {
		return ultimoUsoQuestao;
	}

	public void setUltimoUsoQuestao(Date ultimoUsoQuestao) {
		this.ultimoUsoQuestao = ultimoUsoQuestao;
	}

	public String getVisualizacaoQuestao() {
		return visualizacaoQuestao;
	}

	public void setVisualizacaoQuestao(String visualizacaoQuestao) {
		this.visualizacaoQuestao = visualizacaoQuestao;
	}

	public String getCorpoQuestao() {
		return corpoQuestao;
	}

	public void setCorpoQuestao(String corpoQuestao) {
		this.corpoQuestao = corpoQuestao;
	}

	public String getTituloQuestao() {
		return tituloQuestao;
	}

	public void setTituloQuestao(String tituloQuestao) {
		this.tituloQuestao = tituloQuestao;
	}

	public String getTipoQuestao() {
		return tipoQuestao;
	}

	public void setTipoQuestao(String tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}

}
