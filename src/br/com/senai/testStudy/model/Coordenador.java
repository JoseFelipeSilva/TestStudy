package br.com.senai.testStudy.model;

public class Coordenador extends Pessoa {
	private Integer idCoord;
	private EscolaCliente escola;

	public EscolaCliente getEscola() {
		return escola;
	}

	public void setEscola(EscolaCliente escola) {
		this.escola = escola;
	}

	public Integer getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(Integer idCoord) {
		this.idCoord = idCoord;
	}

}
