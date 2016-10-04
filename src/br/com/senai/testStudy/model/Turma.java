package br.com.senai.testStudy.model;

public class Turma {
	Integer idTurma;
	EscolaCliente escolaTurma;
	String nomeTurma;

	public Integer getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(Integer idTurma) {
		this.idTurma = idTurma;
	}

	public EscolaCliente getEscolaTurma() {
		return escolaTurma;
	}

	public void setEscolaTurma(EscolaCliente escolaTurma) {
		this.escolaTurma = escolaTurma;
	}

	public String getNomeTurma() {
		return nomeTurma;
	}

	public void setNomeTurma(String nomeTurma) {
		this.nomeTurma = nomeTurma;
	}

}
