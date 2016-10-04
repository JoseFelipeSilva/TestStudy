package br.com.senai.testStudy.model;

public class AlternativaSimulado {
	private Integer idAlternativa;
	private QuestaoSimulado questaoSimulado;
	private String corpoAlternativa;
	private String certa;

	public Integer getIdAlternativa() {
		return idAlternativa;
	}

	public void setIdAlternativa(Integer idAlternativa) {
		this.idAlternativa = idAlternativa;
	}

	public QuestaoSimulado getQuestaoSimulado() {
		return questaoSimulado;
	}

	public void setQuestaoSimulado(QuestaoSimulado questaoSimulado) {
		this.questaoSimulado = questaoSimulado;
	}

	public String getCorpoAlternativa() {
		return corpoAlternativa;
	}

	public void setCorpoAlternativa(String corpoAlternativa) {
		this.corpoAlternativa = corpoAlternativa;
	}

	public String getCerta() {
		return certa;
	}

	public void setCerta(String certa) {
		this.certa = certa;
	}

}
