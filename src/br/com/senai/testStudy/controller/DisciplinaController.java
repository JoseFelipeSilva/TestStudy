package br.com.senai.testStudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.DisciplinaDAO;
import br.com.senai.testStudy.model.Disciplina;

@Controller
public class DisciplinaController {
	private static DisciplinaDAO dao;
	@Autowired
	public DisciplinaController(DisciplinaDAO dao) {
		this.dao = dao;
	}
	
	@RequestMapping("formDisciplina")
	public String formAdd(){
		return "formCadDisc";
	}
	
	@RequestMapping("adicionaDisc")
	public String add(Disciplina disc){
		dao.adicionar(disc);
		return "sucesso";
	}
	
	@RequestMapping("listandoDisciplina")
	public String listando(Model model){
		model.addAttribute("listaDISC", dao.listar());
		return "listaDISC";
	}
	
	@RequestMapping("alterandoDisc")
	public String alterando(Model model, Disciplina disciplina){
		model.addAttribute("infoDisc", dao.buscarID(disciplina.getIdDisciplina()));
		return "formAlterDISC";
		
	}
	@RequestMapping("alterarDISC")
	public String alterar(Disciplina disciplina){
		dao.alterar(disciplina);
		return "sucesso";
	}
	@RequestMapping("removendoDisc")
	public String remover(Disciplina disc){
		dao.remover(disc);
		return "sucesso";
	}
	
}
