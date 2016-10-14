package br.com.senai.testStudy.model;

import java.sql.Date;
import java.sql.Time;

public class Log {
	private Integer idLog;
	private String nomeUsuario;
	private String tipoUsuario;
	private String acaoUsuario;
	private Date dataAcao;
	private Time horarioAcao;

	public Integer getIdLog() {
		return idLog;
	}

	public void setIdLog(Integer idLog) {
		this.idLog = idLog;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getAcaoUsuario() {
		return acaoUsuario;
	}

	public void setAcaoUsuario(String acaoUsuario) {
		this.acaoUsuario = acaoUsuario;
	}

	public Date getDataAcao() {
		return dataAcao;
	}

	public void setDataAcao(Date dataAcao) {
		this.dataAcao = dataAcao;
	}

	public Time getHorarioAcao() {
		return horarioAcao;
	}

	public void setHorarioAcao(Time horarioAcao) {
		this.horarioAcao = horarioAcao;
	}

}
