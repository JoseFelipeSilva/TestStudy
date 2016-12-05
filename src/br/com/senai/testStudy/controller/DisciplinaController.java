package br.com.senai.testStudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.DisciplinaDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.util.Util;

@Controller
public class DisciplinaController {
	private final DisciplinaDAO dao;
	private final LogDAO ldao;

	@Autowired
	public DisciplinaController(DisciplinaDAO dao, LogDAO ldao) {
		this.dao = dao;
		this.ldao = ldao;
	}

	@RequestMapping("formDisciplina")
	public String formAdd(HttpSession sessao, Coordenador c) {
		return "formCadDisc";
	}	

	@RequestMapping("adicionaDisc")
	public String add(Disciplina disc, Coordenador c, HttpSession sessao) {
		c = (Coordenador) sessao.getAttribute("coordLogon");
		disc.setPadraoDisciplina("privada");
		disc.setEscola(c.getEscola());
		dao.adicionar(disc);
		Util.addLog(sessao, ldao, this);
		return "sucesso";
	}
	
	@RequestMapping("listandoDisciplinaprivada")
	public String listandoDiscPrivadas(Coordenador c, HttpSession sessao, Model modelo){
		c = (Coordenador) sessao.getAttribute("coordLogon");
		System.out.println(c.getEscola().getIdEmp());
		modelo.addAttribute("listaDISC", dao.listarDiscPrivada(c.getEscola().getIdEmp()));
		return "listaDISC";
		
	}

	@RequestMapping("listandoDisciplina")
	public String listando(Model model, HttpSession session) {
		model.addAttribute("listaDISC", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaDISC";
	}

	@RequestMapping("alterandoDisc")
	public String alterando(Model model, Disciplina disciplina ) {
		model.addAttribute("infoDisc",
				dao.buscarID(disciplina.getIdDisciplina()));
		return "formAlterDISC";

	}

	@RequestMapping("alterarDISC")
	public String alterar(Disciplina disciplina, HttpSession session) {
		dao.alterar(disciplina);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

	@RequestMapping("removendoDisc")
	public String remover(Disciplina disc, HttpSession session) {
		dao.remover(disc);
		Util.addLog(session, ldao, this);
		return "sucesso";
	}

}
