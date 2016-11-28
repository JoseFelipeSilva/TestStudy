package br.com.senai.testStudy.model;

import java.time.LocalDateTime;

public class Log {
	private Integer idLog;
	private String nomeUsuario;
	private String tipoUsuario;
	private String emailUsuario;
	private String acaoUsuario;
	private LocalDateTime dataAcao;

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

	public LocalDateTime getDataAcao() {
		return dataAcao;
	}

	public void setDataAcao(LocalDateTime dataAcao) {
		this.dataAcao = dataAcao;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
}
