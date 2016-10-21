package br.com.senai.testStudy.model;

import com.sun.istack.internal.Nullable;

public class Mensagem {
	private String tituloMensagem;
	private Examinador remetente;
	@Nullable
	private String corpoMensagem;
	private Professor destinatario;
	private QuestaoProva questaoMensagem;

	public String getTituloMensagem() {
		return tituloMensagem;
	}

	public void setTituloMensagem(String tituloMensagem) {
		this.tituloMensagem = tituloMensagem;
	}

	public Examinador getRemetente() {
		return remetente;
	}

	public void setRemetente(Examinador remetente) {
		this.remetente = remetente;
	}

	public String getCorpoMensagem() {
		return corpoMensagem;
	}

	public void setCorpoMensagem(String corpoMensagem) {
		this.corpoMensagem = corpoMensagem;
	}

	public Professor getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Professor destinatario) {
		this.destinatario = destinatario;
	}

	public QuestaoProva getQuestaoMensagem() {
		return questaoMensagem;
	}

	public void setQuestaoMensagem(QuestaoProva questaoMensagem) {
		this.questaoMensagem = questaoMensagem;
	}

}
