package br.com.senai.testStudy.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.senai.testStudy.dao.EscolaClienteDAO;
import br.com.senai.testStudy.dao.LogDAO;
import br.com.senai.testStudy.model.EscolaCliente;
import br.com.senai.testStudy.util.Util;

@Controller
public class EscolaClienteController {
	private final EscolaClienteDAO dao;
	private final LogDAO ldao;

	@Autowired
	public EscolaClienteController(EscolaClienteDAO dao, LogDAO ldao) {
		this.dao = dao;
		this.ldao = ldao;
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
	public String addEscolaCliente(EscolaCliente escola, HttpSession session) {
		dao.adicionar(escola);
		Util.addLog(session, ldao, this);
		return "sucessoPage";
	}

	@RequestMapping("listagemEscolaCliente")
	public String listaOfEscolas(Model model, HttpSession session) {
		model.addAttribute("listaSchools", dao.listar());
		Util.addLog(session, ldao, this);
		return "listaEscolaCliente";
	}

	@RequestMapping("removerEscola")
	public String removerEscola(Model model, EscolaCliente escola, HttpSession session) {
		dao.adicionaMorto(escola.getIdEmp());
		dao.remover(escola);
		Util.addLog(session, ldao, this);
		model.addAttribute("listaSchools", dao.listar());
		return "listaEscolaCliente";
	}

	@RequestMapping("atualizarEscola")
	public String altPageEscola(Model model, EscolaCliente escola) {
		model.addAttribute("altEscola", dao.buscarID(escola.getIdEmp()));
		return "alteraEscolaCliente";
	}

	@RequestMapping("alteracaoEscolaCliente")
	public String alteraEscolaCliente(EscolaCliente escola, Model model, HttpSession session) {
		dao.alterar(escola);
		Util.addLog(session, ldao, this);
		model.addAttribute("listaSchools", dao.listar());
		return "listaEscolaCliente";
	}

	@RequestMapping("backToListOfSchools")
	public String voltarListaEscola(Model model) {
		model.addAttribute("listaSchools", dao.listar());
		return "listaEscolaCliente";
	}
}
