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
		log.setAcaoUsuario(object.getClass().getName() +"."+Thread.currentThread().getStackTrace()[2].getMethodName());
			if (sessao.getAttribute("admLogon") != null) {
				log.setNomeUsuario(((Administrador)sessao.getAttribute("admLogon")).getNome());
				log.setEmailUsuario(((Administrador)sessao.getAttribute("admLogon")).getEmail());
				log.setTipoUsuario(sessao.getAttribute("admLogon").getClass().getName());
			}
			if (sessao.getAttribute("alunoLogon") != null) {
				log.setNomeUsuario(((Aluno)sessao.getAttribute("alunoLogon")).getNomeAluno());
				log.setEmailUsuario(((Aluno)sessao.getAttribute("alunoLogon")).getEmail());
				log.setTipoUsuario(sessao.getAttribute("alunoLogon").getClass().getName());
			}
			if (sessao.getAttribute("coordLogon") != null) {
				log.setNomeUsuario(((Coordenador)sessao.getAttribute("coordLogon")).getNome());
				log.setEmailUsuario(((Coordenador)sessao.getAttribute("coordLogon")).getEmail());
				log.setTipoUsuario(sessao.getAttribute("coordLogon").getClass().getName());
			}
			if (sessao.getAttribute("examLogon") != null) {
				log.setNomeUsuario(((Examinador)sessao.getAttribute("examLogon")).getNome());
				log.setEmailUsuario(((Examinador)sessao.getAttribute("examLogon")).getEmail());
				log.setTipoUsuario(sessao.getAttribute("examLogon").getClass().getName());
			}
			if (sessao.getAttribute("profLogon") != null) {
				log.setNomeUsuario(((Professor)sessao.getAttribute("profLogon")).getNome());
				log.setEmailUsuario(((Professor)sessao.getAttribute("profLogon")).getEmail());
				log.setTipoUsuario(sessao.getAttribute("profLogon").getClass().getName());
			}
		logDAO.adicionareAcao(log);
	}
}
