package br.com.senai.testStudy.util;

import javax.servlet.http.HttpSession;

import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.dao.ProvaAgendadaDAO;
import br.com.senai.testStudy.model.Administrador;
import br.com.senai.testStudy.model.Aluno;
import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.Examinador;
import br.com.senai.testStudy.model.Log;
import br.com.senai.testStudy.model.Professor;

public class Util {
	public static void acessandoNotificacoes(HttpSession sessao){
		// o objetivo aqui é criar duas listas, e comparar se tem prova marcada na turma do aluno que está logado,
		// se sim exibe no painel de notificações...
		sessao.setAttribute("notificacoes", ProvaAgendadaDAO.listar(((Aluno) sessao.getAttribute("alunoLogon")).getTurmaAluno().getIdTurma()));
	}
	public static void addLog(HttpSession sessao, LogDAO logDAO, Object object){
		Log log = new Log();
		log.setAcaoUsuario(object.getClass().getName());
		while (sessao.getAttributeNames().hasMoreElements()) {
			String atributo = (String) sessao.getAttributeNames().nextElement();
			if (sessao.getAttribute(atributo).getClass() == Administrador.class) {
				log.setNomeUsuario(((Administrador)sessao.getAttribute(atributo)).getNome());
				log.setEmailUsuario(((Administrador)sessao.getAttribute(atributo)).getEmail());
				log.setTipoUsuario(sessao.getAttribute(atributo).getClass().getName());
			}
			if (sessao.getAttribute(atributo).getClass() == Aluno.class) {
				log.setNomeUsuario(((Aluno)sessao.getAttribute(atributo)).getNomeAluno());
				log.setEmailUsuario(((Aluno)sessao.getAttribute(atributo)).getEmail());
				log.setTipoUsuario(sessao.getAttribute(atributo).getClass().getName());
			}
			if (sessao.getAttribute(atributo).getClass() == Coordenador.class) {
				log.setNomeUsuario(((Coordenador)sessao.getAttribute(atributo)).getNome());
				log.setEmailUsuario(((Coordenador)sessao.getAttribute(atributo)).getEmail());
				log.setTipoUsuario(sessao.getAttribute(atributo).getClass().getName());
			}
			if (sessao.getAttribute(atributo).getClass() == Examinador.class) {
				log.setNomeUsuario(((Examinador)sessao.getAttribute(atributo)).getNome());
				log.setEmailUsuario(((Examinador)sessao.getAttribute(atributo)).getEmail());
				log.setTipoUsuario(sessao.getAttribute(atributo).getClass().getName());
			}
			if (sessao.getAttribute(atributo).getClass() == Professor.class) {
				log.setNomeUsuario(((Professor)sessao.getAttribute(atributo)).getNome());
				log.setEmailUsuario(((Professor)sessao.getAttribute(atributo)).getEmail());
				log.setTipoUsuario(sessao.getAttribute(atributo).getClass().getName());
			}
			
		}
		logDAO.adicionareAcao(log);
	}
}
