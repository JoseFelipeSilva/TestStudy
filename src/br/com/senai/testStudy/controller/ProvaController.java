package br.com.senai.testStudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.ProfessorDAO;
import br.com.senai.testStudy.dao.ProvaDAO;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.model.Prova;

@Controller
public class ProvaController {
	private final ProvaDAO dao;
	private final ProfessorDAO pdao;

	@Autowired
	public ProvaController(ProvaDAO dao, ProfessorDAO pdao) {
		this.dao = dao;
		this.pdao = pdao;
	}

	@RequestMapping("newProva")
	public String addPageProva(Model model) {
		model.addAttribute("LProfs", pdao.listar());
		return "addProva";
	}

	@RequestMapping("adicionarProva")
	public String addPagina(Professor prof, Prova prova) {
		prova.setProfessor(prof);
		dao.adicionar(prova);
		return "sucessoPage";
	}

	@RequestMapping("ListagemProva")
	public String listaDeProvas(Model model) {
		model.addAttribute("LProvas", dao.listar());
		return "listaProva";
	}

	@RequestMapping("removerProva")
	public String removerProva(Prova prova, Model model) {
		dao.remover(prova);
		model.addAttribute("LProvas", dao.listar());
		return "listaProva";
	}

	@RequestMapping("alterarProva")
	public String altPageProva(Prova prova, Model model) {
		model.addAttribute("p", dao.buscarID(prova.getIdProva()));
		model.addAttribute("LProfs", pdao.listar());
		return "alteraProva";
	}

	@RequestMapping("backToListOfProvas")
	public String backToListOfProvas(Model model) {
		model.addAttribute("LProvas", dao.listar());
		return "listaProva";
	}

	@RequestMapping("alterandoProva")
	public String alterandoProva(Model model, Prova prova, Professor prof) {
		prova.setProfessor(prof);
		dao.alterar(prova);
		model.addAttribute("LProvas", dao.listar());
		return "listaProva";
	}
}
