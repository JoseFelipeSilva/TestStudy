package br.com.senai.testStudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.EscolaClienteDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.dao.TurmaDAO;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.model.Turma;
import br.com.senai.testStudy.util.Util;

@Controller
public class TurmaController {
	private final TurmaDAO dao;
	private final EscolaClienteDAO edao;
	private final LogDAO ldao;

	@Autowired
	public TurmaController(TurmaDAO dao, EscolaClienteDAO edao, LogDAO ldao) {
		this.dao = dao;
		this.edao = edao;
		this.ldao = ldao;
	}

	@RequestMapping("newTurma")
	public String addPageTurma(Model model) {
		model.addAttribute("LEscola", edao.listar());
		return "addTurma";
	}

	@RequestMapping("adicionamentoTurma")
	public String addTurma(Turma turma, EscolaCliente escola, HttpSession session) {
		turma.setEscolaTurma(escola);
		dao.adicionar(turma);
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}

	@RequestMapping("ListagemTurma")
	public String listaOfTurmas(Model model, HttpSession session) {
		model.addAttribute("LTurmas", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaTurma";
	}

	@RequestMapping("removerTurma")
	public String removerTurma(Turma turma, Model model, HttpSession session) {
		dao.adicionarMorto(turma.getIdTurma());
		dao.remover(turma);
		model.addAttribute("LTurmas", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaTurma";
	}

	@RequestMapping("alterarTurma")
	public String altPageTurma(Turma turma, Model model, EscolaCliente escola) {
		model.addAttribute("t", dao.buscarID(turma.getIdTurma()));
		model.addAttribute("LEscola", edao.listar());
		return "alteraTurma";
	}

	@RequestMapping("backToListOfTurma")
	public String backListOfTurmas(Model model) {
		model.addAttribute("LTurmas", dao.listar());
		return "listaTurma";
	}

	@RequestMapping("alteracaoTurma")
	public String alteraTurma(Model model, Turma turma, EscolaCliente escola, HttpSession session) {
		turma.setEscolaTurma(escola);
		dao.alterar(turma);
		model.addAttribute("LTurmas", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaTurma";
	}
}
