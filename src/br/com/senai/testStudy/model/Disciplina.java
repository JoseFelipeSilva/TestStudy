package br.com.senai.testStudy.model;

public class Disciplina {
	private Integer idDisciplina;
	private String nomeDisciplina;
	private String padraoDisciplina;
	private EscolaCliente escola;

	public String getPadraoDisciplina() {
		return padraoDisciplina;
	}

	public void setPadraoDisciplina(String padraoDisciplina) {
		this.padraoDisciplina = padraoDisciplina;
	}

	public EscolaCliente getEscola() {
		return escola;
	}

	public void setEscola(EscolaCliente escola) {
		this.escola = escola;
	}

	public Integer getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(Integer idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public String getNomeDisciplina() {
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

}
