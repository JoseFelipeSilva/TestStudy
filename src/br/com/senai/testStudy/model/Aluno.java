package br.com.senai.testStudy.model;

public class Aluno extends Pessoa {

	Integer idAluno;
	EscolaCliente escolaAluno;
	Turma turmaAluno;

	public Integer getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Integer idAluno) {
		this.idAluno = idAluno;
	}

	public EscolaCliente getEscolaAluno() {
		return escolaAluno;
	}

	public void setEscolaAluno(EscolaCliente escolaAluno) {
		this.escolaAluno = escolaAluno;
	}

	public Turma getTurmaAluno() {
		return turmaAluno;
	}

	public void setTurmaAluno(Turma turmaAluno) {
		this.turmaAluno = turmaAluno;
	}

}
