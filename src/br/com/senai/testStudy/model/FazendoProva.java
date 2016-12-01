package br.com.senai.testStudy.model;

public class FazendoProva {
	private Aluno aluno;
	private ProvaAgendada provaAgendada;
	private Integer idFazendoProva;
	private String resposta;
	private Alternativa alternativa;

	public Alternativa getAlternativa() {
		return alternativa;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public ProvaAgendada getProvaAgendada() {
		return provaAgendada;
	}

	public void setProvaAgendada(ProvaAgendada provaAgendada) {
		this.provaAgendada = provaAgendada;
	}

	public Integer getIdFazendoProva() {
		return idFazendoProva;
	}

	public void setIdFazendoProva(Integer idFazendoProva) {
		this.idFazendoProva = idFazendoProva;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

}
