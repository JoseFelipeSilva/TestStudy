package br.com.senai.testStudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.EscolaClienteDAO;
import br.com.senai.testStudy.model.EscolaCliente;

@Controller
public class EscolaClienteController {
	private final EscolaClienteDAO dao;

	@Autowired
	public EscolaClienteController(EscolaClienteDAO dao) {
		this.dao = dao;
	}

	@RequestMapping("backToIndex")
	public String voltarIndex() {
		return "redirect:index.jsp";
	}

	@RequestMapping("newEscolaCliente")
	public String addPageEscolaCliente(Model model) {
		return "addEscolaCliente";
	}

	@RequestMapping("adicionamentoEscolaCliente")
	public String addEscolaCliente(EscolaCliente escola) {
		dao.adicionar(escola);
		return "sucessoPage";
	}

	@RequestMapping("listagemEscolaCliente")
	public String listaOfEscolas(Model model) {
		model.addAttribute("listaSchools", dao.listar());
		return "listaEscolaCliente";
	}

	@RequestMapping("removerEscola")
	public String removerEscola(EscolaCliente escola) {
		dao.remover(escola);
		return "redirect:WebContent/WEB-INF/views/listaEscolaCliente.jsp";
	}

	@RequestMapping("atualizarEscola")
	public String altPageEscola(Model model, EscolaCliente escola) {
		model.addAttribute("altEscola", dao.buscarID(escola.getIdEmp()));
		return "alteraEscolaCliente";
	}

	@RequestMapping("alteracaoEscolaCliente")
	public String alteraEscolaCliente(EscolaCliente escola, Model model) {
		dao.alterar(escola);
		model.addAttribute("listaSchools", dao.listar());
		return "listaEscolaCliente";
	}
	
	@RequestMapping("backToListOfSchools")
	public String voltarListaEscola(Model model){
		model.addAttribute("listaSchools", dao.listar());
		return "listaEscolaCliente";
	}
}
