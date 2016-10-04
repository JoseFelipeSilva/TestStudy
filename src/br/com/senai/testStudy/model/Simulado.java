package br.com.senai.testStudy.model;

import java.sql.Date;

public class Simulado {
	Integer idSimulado;
	Aluno alunoSimulado;
	String visualizacaoSimulado; // Se o simulado está visível para o público em
									// geral

	public Integer getIdSimulado() {
		return idSimulado;
	}

	public void setIdSimulado(Integer idSimulado) {
		this.idSimulado = idSimulado;
	}

	public Aluno getAlunoSimulado() {
		return alunoSimulado;
	}

	public void setAlunoSimulado(Aluno alunoSimulado) {
		this.alunoSimulado = alunoSimulado;
	}

	public String getVisualizacaoSimulado() {
		return visualizacaoSimulado;
	}

	public void setVisualizacaoSimulado(String visualizacaoSimulado) {
		this.visualizacaoSimulado = visualizacaoSimulado;
	}

}
