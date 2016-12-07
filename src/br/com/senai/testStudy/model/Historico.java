package br.com.senai.testStudy.model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Historico {
	private Integer idHistorico;
	private Aluno aluno;
	private Prova prova;
	private Double notaProva;
	private LocalDateTime dataProvaHistorico;
	private Double notaSimulado;
	private LocalDateTime dataSimuladoHistorico;

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

	public LocalDateTime getDataProvaHistorico() {
		return dataProvaHistorico;
	}

	public void setDataProvaHistorico(LocalDateTime dataProvaHistorico) {
		this.dataProvaHistorico = dataProvaHistorico;
	}

	public Double getNotaSimulado() {
		return notaSimulado;
	}

	public void setNotaSimulado(Double notaSimulado) {
		this.notaSimulado = notaSimulado;
	}

	public LocalDateTime getDataSimuladoHistorico() {
		return dataSimuladoHistorico;
	}

	public void setDataSimuladoHistorico(LocalDateTime dataSimuladoHistorico) {
		this.dataSimuladoHistorico = dataSimuladoHistorico;
	}

}
