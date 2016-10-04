package br.com.senai.testStudy.model;

public class Coordenador extends Pessoa {
	Integer idCoord;
	EscolaCliente escolaCliente;

	public Integer getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(Integer idCoord) {
		this.idCoord = idCoord;
	}

	public EscolaCliente getEscolaCliente() {
		return escolaCliente;
	}

	public void setEscolaCliente(EscolaCliente escolaCliente) {
		this.escolaCliente = escolaCliente;
	}

}
