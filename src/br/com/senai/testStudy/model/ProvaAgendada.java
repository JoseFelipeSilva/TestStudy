package br.com.senai.testStudy.model;

import java.sql.Date;
import java.sql.Time;

public class ProvaAgendada {
	private Integer idProvaAgendada;
	private Time horaInicio;
	private Time horaTermino;
	private Date dataInicio;
	private Date dataTermino;
	private Date dataRealizacao;
	private Turma turma;
	private Prova prova;

	public Integer getIdProvaAgendada() {
		return idProvaAgendada;
	}

	public void setIdProvaAgendada(Integer idProvaAgendada) {
		this.idProvaAgendada = idProvaAgendada;
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
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

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}
