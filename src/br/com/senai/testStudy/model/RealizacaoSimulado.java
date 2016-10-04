package br.com.senai.testStudy.model;

import java.sql.Date;
import java.sql.Time;

public class RealizacaoSimulado {
	private Integer idRealizacaoSimulado;
	private Simulado idSimulado;
	private Time horaInicio;
	private Time horaTermino;
	private Date dataInicio;
	private Date dataTermino;

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

	public Time getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Time horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Time getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(Time horaTermino) {
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
