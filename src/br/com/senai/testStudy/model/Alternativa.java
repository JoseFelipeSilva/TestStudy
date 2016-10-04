package br.com.senai.testStudy.model;

public class Alternativa {
	Integer idAlternativa;
	QuestaoProva questaoAlternativa;
	String corpoAlternativa;
	String certa;

	public Integer getIdAlternativa() {
		return idAlternativa;
	}

	public void setIdAlternativa(Integer idAlternativa) {
		this.idAlternativa = idAlternativa;
	}

	public QuestaoProva getQuestaoAlternativa() {
		return questaoAlternativa;
	}

	public void setQuestaoAlternativa(QuestaoProva questaoAlternativa) {
		this.questaoAlternativa = questaoAlternativa;
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
