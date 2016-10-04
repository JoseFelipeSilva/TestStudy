package br.com.senai.testStudy.model;

import java.sql.Date;

public class Historico {
	Integer idHistorico;
	Aluno aluno;
	Prova prova;
	Double notaProva;
	Date dataProvaHistorico;
	Double notaSimulado;
	Date dataSimuladoHistorico;

	public Integer getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(Integer idHistorico) {
		this.idHistorico = idHistorico;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public Double getNotaProva() {
		return notaProva;
	}

	public void setNotaProva(Double notaProva) {
		this.notaProva = notaProva;
	}

	public Date getDataProvaHistorico() {
		return dataProvaHistorico;
	}

	public void setDataProvaHistorico(Date dataProvaHistorico) {
		this.dataProvaHistorico = dataProvaHistorico;
	}

	public Double getNotaSimulado() {
		return notaSimulado;
	}

	public void setNotaSimulado(Double notaSimulado) {
		this.notaSimulado = notaSimulado;
	}

	public Date getDataSimuladoHistorico() {
		return dataSimuladoHistorico;
	}

	public void setDataSimuladoHistorico(Date dataSimuladoHistorico) {
		this.dataSimuladoHistorico = dataSimuladoHistorico;
	}

}
