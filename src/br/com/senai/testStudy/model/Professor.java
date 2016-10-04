package br.com.senai.testStudy.model;

public class Professor extends Pessoa {
	private Integer idProfessor;
	private EscolaCliente escolaProfessor;

	public Integer getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(Integer idProfessor) {
		this.idProfessor = idProfessor;
	}

	public EscolaCliente getEscolaProfessor() {
		return escolaProfessor;
	}

	public void setEscolaProfessor(EscolaCliente escolaProfessor) {
		this.escolaProfessor = escolaProfessor;
	}

}
