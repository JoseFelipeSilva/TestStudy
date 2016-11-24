package br.com.senai.testStudy.util;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import br.com.senai.testStudy.dao.ProvaAgendadaDAO;
import br.com.senai.testStudy.model.Aluno;

public class Util {
	public static void acessandoNotificacoes(HttpSession sessao){
		// o objetivo aqui é criar duas listas, e comparar se tem prova marcada na turma do aluno que está logado,
		// se sim exibe no painel de notificações...
		sessao.setAttribute("notificacoes", ProvaAgendadaDAO.listar(((Aluno) sessao.getAttribute("alunoLogon")).getTurmaAluno().getIdTurma()));
	}
}
