package br.com.senai.testStudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.DisciplinaDAO;
import br.com.senai.testStudy.model.Coordenador;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.EscolaCliente;

@Controller
public class DisciplinaController {
	private final DisciplinaDAO dao;

	@Autowired
	public DisciplinaController(DisciplinaDAO dao) {
		this.dao = dao;
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
	public String listando(Model model) {
		model.addAttribute("listaDISC", dao.listar());
		return "listaDISC";
	}

	@RequestMapping("alterandoDisc")
	public String alterando(Model model, Disciplina disciplina) {
		model.addAttribute("infoDisc",
				dao.buscarID(disciplina.getIdDisciplina()));
		return "formAlterDISC";

	}

	@RequestMapping("alterarDISC")
	public String alterar(Disciplina disciplina) {
		dao.alterar(disciplina);
		return "sucesso";
	}

	@RequestMapping("removendoDisc")
	public String remover(Disciplina disc) {
		dao.remover(disc);
		return "sucesso";
	}

}
