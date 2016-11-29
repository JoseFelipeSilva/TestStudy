package br.com.senai.testStudy.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.ProvaAgendadaDAO;
import br.com.senai.testStudy.dao.ProvaDAO;
import br.com.senai.testStudy.dao.QuestaoProvaDAO;
import br.com.senai.testStudy.dao.TurmaDAO;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.Turma;

@Controller
public class ProvaAgendadaController {
	private final ProvaAgendadaDAO dao;
	private final ProvaDAO pdao;
	private final TurmaDAO tdao;
	private final QuestaoProvaDAO qpdao;

	@Autowired
	public ProvaAgendadaController(QuestaoProvaDAO qpdao,
			TurmaDAO tdao, ProvaAgendadaDAO dao, ProvaDAO pdao) {
		this.dao = dao;
		this.pdao = pdao;
		this.tdao = tdao;
		this.qpdao = qpdao;
	}

	@RequestMapping("newProvaAgendada")
	public String addPageProvaAgendada(Model model, HttpSession session,
			Professor p) {
		p = (Professor) session.getAttribute("profLogon");
		model.addAttribute("LProvas", pdao.listar(p.getIdProfessor()));
		model.addAttribute("LTurmas", tdao.listar());
		return "addProvaAgendada";
	}

	@RequestMapping("fazerProvaAgendada")
	public String fazerProva(Model model, HttpSession session,
			ProvaAgendada provaAgendada) {
		List<ProvaAgendada> notificacao = (List<ProvaAgendada>) session.getAttribute("notificacoes");
		for (ProvaAgendada provaAgendada2 : notificacao) {
			if (provaAgendada2.getIdProvaAgendada() == provaAgendada.getIdProvaAgendada()) {
				provaAgendada = dao.buscarID(provaAgendada.getIdProvaAgendada());
				session.setAttribute("provaParaFazer", provaAgendada);
				model.addAttribute("QuestoesDaProvaParaFazer", qpdao.listarQuestoesDaProva(provaAgendada.getProva().getIdProva()));
				
			}

		}
		return "resumoDaProva";
	}

	@RequestMapping("adicionarProvaAgendada")
	public String adicionarProvaAgendada(Turma t, Prova p, ProvaAgendada pa) {
		pa.setProva(p);
		pa.setTurma(t);
		dao.adicionar(pa);
		return "sucessoPage";
	}
}
