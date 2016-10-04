package br.com.senai.testStudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.MateriaDAO;
import br.com.senai.testStudy.model.Disciplina;
import br.com.senai.testStudy.model.Materia;

@Controller
public class MateriaController {
	private final MateriaDAO dao;

	@Autowired
	public MateriaController(MateriaDAO dao) {
		this.dao = dao;

	}
	
	@RequestMapping("formMateria")
	public String formAdd(Model model){
		model.addAttribute("disc", dao.listarDisc());
		return "formCadMat";
	}
	@RequestMapping("cadMateria")
	public String cadMat(Materia m, Disciplina d){
		m.setDisciplina(d);
		dao.adicionar(m);
		return "sucesso";
	}
	@RequestMapping("listandoMat")
	public String listandoMat(Model model){
		model.addAttribute("infoMAT", dao.listar());
		return "listaMAT";
	}
	@RequestMapping("alterandoMateria")
	public String alterando(Model model, Materia m){
		model.addAttribute("buscaId", dao.buscarID(m.getIdMateria()));
		model.addAttribute("lista", dao.listar());
		return "formAlterMat";
	}
	@RequestMapping("alterarMat")
	public String alterar(Materia m, Disciplina d){
		m.setDisciplina(d);
		dao.alterar(m);
		return "sucesso";
	}
}
