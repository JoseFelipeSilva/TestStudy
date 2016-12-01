package br.com.senai.testStudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.dao.MateriaDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Materia;
import br.com.senai.testStudy.model.Professor;
import br.com.senai.testStudy.util.Util;

@Controller
public class MateriaController {
	private final MateriaDAO dao;
	private final LogDAO ldao;

	@Autowired
	public MateriaController(MateriaDAO dao, LogDAO ldao) {
		this.dao = dao;
		this.ldao = ldao;

	}

	@RequestMapping("formMateria")
	public String formAdd(Model model, Professor p, HttpSession sessao) {
		p = (Professor) sessao.getAttribute("profLogon");
		model.addAttribute("disc", dao.listarDisc(p.getEscolaProfessor().getIdEmp()));
		return "formCadMat";
	}

	@RequestMapping("cadMateria")
	public String cadMat(Materia m, Disciplina d, HttpSession session) {
		m.setDisciplina(d);
		System.out.println(m.getNomeMateria());
		dao.adicionar(m);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

	@RequestMapping("listandoMat")
	public String listandoMat(Model model, HttpSession session) {
		model.addAttribute("infoMAT", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaMAT";
	}

	@RequestMapping("alterandoMateria")
	public String alterando(Model model, Materia m) {
		model.addAttribute("buscaId", dao.buscarID(m.getIdMateria()));
		model.addAttribute("lista", dao.listar());
		return "formAlterMat";
	}

	@RequestMapping("alterarMat")
	public String alterar(Materia m, Disciplina d, HttpSession session) {
		m.setDisciplina(d);
		dao.alterar(m);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

	@RequestMapping("consultarMateria")
	public String consultar(Model modelo, Disciplina d, Professor p, HttpSession sessao) {
		p = (Professor) sessao.getAttribute("profLogon");
		modelo.addAttribute("materia", dao.listarMateria(d));
		modelo.addAttribute("disc", dao.listarDisc(p.getEscolaProfessor().getIdEmp()));
		modelo.addAttribute("disciplinaSelecionada",
				dao.buscarID(d.getIdDisciplina()));
		return "formCadQP";
	}
}
