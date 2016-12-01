package br.com.senai.testStudy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import br.com.senai.testStudy.dao.AlternativaDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.dao.ProvaAgendadaDAO;
import br.com.senai.testStudy.dao.ProvaDAO;
import br.com.senai.testStudy.dao.QuestaoProvaDAO;
import br.com.senai.testStudy.dao.TurmaDAO;
import br.com.senai.testStudy.model.Alternativa;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.QuestaoProva;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.Util;

@Controller
public class ProvaAgendadaController {
	private final ProvaAgendadaDAO dao;
	private final ProvaDAO pdao;
	private final TurmaDAO tdao;
	private final QuestaoProvaDAO qpdao;
	private final LogDAO ldao;
	private final AlternativaDAO idao;

	@Autowired

	public ProvaAgendadaController(QuestaoProvaDAO qpdao, TurmaDAO tdao,
			ProvaAgendadaDAO dao, ProvaDAO pdao, LogDAO ldao,
			AlternativaDAO idao) {

		this.dao = dao;
		this.pdao = pdao;
		this.tdao = tdao;
		this.qpdao = qpdao;
		this.ldao = ldao;
		this.idao = idao;
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
		List<ProvaAgendada> notificacao = (List<ProvaAgendada>) session
				.getAttribute("notificacoes");

		List<QuestaoProva>questoes = new ArrayList<QuestaoProva>();
		for (ProvaAgendada provaAgendada2 : notificacao) {			
				if (provaAgendada2.getIdProvaAgendada() == provaAgendada.getIdProvaAgendada()) {
	  				provaAgendada = dao.buscarID(provaAgendada.getIdProvaAgendada());
	 				session.setAttribute("provaParaFazer", provaAgendada);
	 				questoes = qpdao.listarQuestoesDaProva(provaAgendada.getProva().getIdProva());
	 				model.addAttribute("QuestoesDaProvaParaFazer", questoes);
	 				provaAgendada.getProva().setQuestoes(questoes);
	 				for (int i = 0; i < questoes.size(); i++) {
	 						provaAgendada.getProva().getQuestoes().get(i).setAlternativas(idao.listarPorQuestao(questoes.get(i).getIdQuestaoProva()));
	 				}
	 				
	  			} 
				session.setAttribute("provaParaFazer", provaAgendada);
			}

		

		Util.addLog(session, ldao, this);
		return "resumoDaProva";

	}

	@RequestMapping("adicionarProvaAgendada")
	public String adicionarProvaAgendada(Turma t, Prova p, ProvaAgendada pa,
			HttpSession session) {
		pa.setProva(p);
		pa.setTurma(t);
		dao.adicionar(pa);
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}
	
	@RequestMapping("resolverProva")
	public String resolverProva(Model modelo, HttpSession session, ProvaAgendada provaAgendada){
		provaAgendada = (ProvaAgendada) session.getAttribute("provaParaFazer");	
		Integer count = 0;		
		session.setAttribute("questao", provaAgendada.getProva().getQuestoes().get(0));		
		session.setAttribute("alternativas", provaAgendada.getProva().getQuestoes().get(0).getAlternativas());
		session.setAttribute("nQuestoes", provaAgendada.getProva().getnQuestoes());
		session.setAttribute("count", count);
		
		return "resolucaoDeProva";
	}
}






























