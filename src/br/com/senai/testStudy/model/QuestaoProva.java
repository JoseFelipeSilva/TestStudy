package br.com.senai.testStudy.model;

import java.sql.Date;

public class QuestaoProva {
	private Integer idQuestaoProva;
	private Integer usoQuestao; // Quantas vezes a quest�o j� foi usada
	private Date ultimoUsoQuestao;
	private String visualizacaoQuestao; // Mostra se a quest�o est� dispon�vel
										// para ser
	// usada novamente
	private String corpoQuestao;
	private String tituloQuestao;
	private String tipoQuestao; // Se a quest�o � de alternativa ou dissertativa
	private Materia materia;
	private Integer dificuldade;
	private String disponibilidadeQuestao; // Se a quest�o estar� dispon�vel para o p�blico em geral
	private String statusQuestao; // diz respeito ao status da quest�o em rela��o ao examinador (pendente, vista, aceita, rejeitada...)
	
	

	public String getDisponibilidadeQuestao() {
		return disponibilidadeQuestao;
	}

	public void setDisponibilidadeQuestao(String disponibilidadeQuestao) {
		this.disponibilidadeQuestao = disponibilidadeQuestao;
	}

	public String getStatusQuestao() {
		return statusQuestao;
	}

	public void setStatusQuestao(String statusQuestao) {
		this.statusQuestao = statusQuestao;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Integer getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(Integer dificuldade) {
		this.dificuldade = dificuldade;
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
