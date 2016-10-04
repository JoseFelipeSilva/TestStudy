package br.com.senai.testStudy.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class ProvaAgendada {
	Integer idProvaAgendada;
	Prova prova;
	LocalDateTime horaInicio;
	LocalDateTime horaTermino;
	Date dataInicio;
	Date dataTermino;
	Date dataRealizacao;
	Turma turma;
	

}
