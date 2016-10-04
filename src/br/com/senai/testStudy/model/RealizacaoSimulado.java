package br.com.senai.testStudy.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class RealizacaoSimulado {
	Integer idRealizacaoSimulado;
	Simulado idSimulado;
	Date dataRealizacao;
	LocalDateTime horaInicio;
	LocalDateTime horaTermino;
	Date dataInicio;
	Date dataTermino;

	public Integer getIdRealizacaoSimulado() {
		return idRealizacaoSimulado;
	}

	public void setIdRealizacaoSimulado(Integer idRealizacaoSimulado) {
		this.idRealizacaoSimulado = idRealizacaoSimulado;
	}

	public Simulado getIdSimulado() {
		return idSimulado;
	}

	public void setIdSimulado(Simulado idSimulado) {
		this.idSimulado = idSimulado;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalDateTime getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(LocalDateTime horaTermino) {
		this.horaTermino = horaTermino;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

}
