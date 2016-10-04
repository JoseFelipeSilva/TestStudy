package br.com.senai.testStudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.ProvaAgendadaDAO;
import br.com.senai.testStudy.dao.ProvaDAO;
import br.com.senai.testStudy.dao.TurmaDAO;
import br.com.senai.testStudy.model.Prova;
import br.com.senai.testStudy.model.ProvaAgendada;
import br.com.senai.testStudy.model.Turma;

@Controller
public class ProvaAgendadaController {
	private final ProvaAgendadaDAO dao;
	private final ProvaDAO pdao;
	private final TurmaDAO tdao;

	@Autowired
	public ProvaAgendadaController(TurmaDAO tdao, ProvaAgendadaDAO dao,
			ProvaDAO pdao) {
		this.dao = dao;
		this.pdao = pdao;
		this.tdao = tdao;
	}

	@RequestMapping("newProvaAgendada")
	public String addPageProvaAgendada(Model model) {
		model.addAttribute("LProvas", pdao.listar());
		model.addAttribute("LTurmas", tdao.listar());
		return "addProvaAgendada";
	}

	@RequestMapping("adicionarProvaAgendada")
	public String adicionarProvaAgendada(Turma t, Prova p, ProvaAgendada pa) {
		pa.setProva(p);
		pa.setTurma(t);
		dao.adicionar(pa);
		return "sucessoPage";
	}
}
