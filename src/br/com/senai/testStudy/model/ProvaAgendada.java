package br.com.senai.testStudy.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;


public class ProvaAgendada {
	private Integer idProvaAgendada;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dataInicio;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dataTermino;
	private Integer duracao;
	private Turma turma;
	private Prova prova;
	public Integer getIdProvaAgendada() {
		return idProvaAgendada;
	}
	public void setIdProvaAgendada(Integer idProvaAgendada) {
		this.idProvaAgendada = idProvaAgendada;
	}
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDateTime getDataTermino() {
		return dataTermino;
	}
	public void setDataTermino(LocalDateTime dataTermino) {
		this.dataTermino = dataTermino;
	}
	public Turma getTurma() {
		return turma;
	}
	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	public Prova getProva() {
		return prova;
	}
	public void setProva(Prova prova) {
		this.prova = prova;
	}
	@Override
	public String toString() {
		return "ProvaAgendada [idProvaAgendada=" + idProvaAgendada
				+ ", dataInicio=" + dataInicio + ", dataTermino=" + dataTermino
				+ ", turma=" + turma + ", prova=" + prova + "]";
	}
	public Integer getDuracao() {
		return duracao;
	}
	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}
}
